# FINAL PROJECT - BASED ON ASSIGNMENT 10

import cv2
import logging
import numpy as np
import os
import random

def normalizeImage(img):
    out = np.zeros(img.shape, dtype=np.float)
    out=img-img.min()
    out=out*255/out.max()
    return np.uint8(out)

def linearWeight(pixel_value):
    pixel_range_min = 0.0
    pixel_range_max = 255.0
    pixel_range_mid = 0.5 * (pixel_range_min + pixel_range_max)
    weight = 0.0
    if pixel_value  > pixel_range_mid:
        weight = float(pixel_range_max - pixel_value)
    else:
        weight = float(pixel_value)
    return weight

def getYXLocations(image, intensity_value):
    return np.where(image==intensity_value)

def computeResponseCurve(pixels, log_exposures, smoothing_lambda,
                         weighting_function):
    pix_range = pixels.shape[0]
    num_images = len(log_exposures)
    mat_A = np.zeros((num_images * pix_range + pix_range - 1, pix_range * 2),
                     dtype=np.float64)
    mat_b = np.zeros((mat_A.shape[0], 1), dtype=np.float64)
    idx_ctr = 0  # index counter
    for i in xrange(pix_range):
        for j in xrange(num_images):
            wij = weighting_function(pixels[i, j])

            mat_A[idx_ctr,pixels[i,j]]=wij
            mat_A[idx_ctr,i+255]=-wij
            mat_b[idx_ctr,0]=wij*log_exposures[j]
            idx_ctr = idx_ctr + 1

    idx = pix_range * num_images
    for i in xrange(pix_range - 1):
        mat_A[idx + i, i] = smoothing_lambda * weighting_function(i)
        mat_A[idx + i, i + 1] = -2 * smoothing_lambda * weighting_function(i)
        mat_A[idx + i, i + 2] = smoothing_lambda * weighting_function(i)

    mat_A[-1, (pix_range / 2) + 1] = 0
    A_inv=np.linalg.pinv(mat_A)
    x=A_inv.dot(mat_b)
    g = x[0:pix_range]
    return g[:,0]

def readImages(image_dir, resize=False):
    file_extensions = ["jpg", "jpeg", "png", "bmp","tif"]
    image_files = sorted(os.listdir(image_dir))
    for img in image_files:
        if img.split(".")[-1].lower() not in file_extensions:
            image_files.remove(img)
    num_images = len(image_files)
    images = [None] * num_images
    images_gray = [None] * num_images
    for image_idx in xrange(num_images):
        images[image_idx] = cv2.imread(os.path.join(image_dir,
                                                    image_files[image_idx]))
        images_gray[image_idx] = cv2.cvtColor(images[image_idx],
                                              cv2.COLOR_BGR2GRAY)
        if resize:
            images[image_idx] = images[image_idx][::4,::4]
            images_gray[image_idx] = images_gray[image_idx][::4,::4]
    return images, images_gray

def computeRM(image_dir, log_exposure_times, smoothing_lambda = 100,
               resize = False):
    print 'computing Radiance Map...'
    images, images_gray = readImages(image_dir, resize)
    num_images = len(images)

    pixel_range_min = 0.0
    pixel_range_max = 255.0
    pixel_range_mid = 0.5 * (pixel_range_min + pixel_range_max)
    num_points = int(pixel_range_max + 1)
    image_size = images[0].shape[0:2]
    if len(images[0].shape) == 2:
        num_channels = 1
        logging.warning("WARNING: This is a single channel image. This code " +\
                        "has not been fully tested on single channel images.")
    elif len(images[0].shape) == 3:
        num_channels = images[0].shape[2]
    else:
        logging.error("ERROR: Image matrix shape is of size: " + 
                      str(images[0].shape))

    locations = np.zeros((256, 2, 3), dtype=np.uint16)

    for channel in xrange(num_channels):
        for cur_intensity in xrange(num_points):

            mid = np.round(num_images / 2)

            y_locs, x_locs = getYXLocations(images[mid][:,:,channel],
                                            cur_intensity)
            if len(y_locs) < 1:
                logging.info("Pixel intensity: " + str(cur_intensity) + 
                             " not found.")
            else:
                random_idx = random.randint(0, len(y_locs) - 1)
                locations[cur_intensity, :, channel] = y_locs[random_idx], \
                                                       x_locs[random_idx]

    intensity_values = np.zeros((num_points, num_images, num_channels),
                                dtype=np.uint8)
    for image_idx in xrange(num_images):
        for channel in xrange(num_channels):
            intensity_values[:, image_idx, channel] = \
                images[image_idx][locations[:, 0, channel],
                                  locations[:, 1, channel],
                                  channel]

    response_curve = np.zeros((256, num_channels), dtype=np.float64)

    for channel in xrange(num_channels):
        response_curve[:, channel] = \
            computeResponseCurve(intensity_values[:, :, channel],
                                 log_exposure_times,
                                 smoothing_lambda,
                                 linearWeight)
    np.savetxt("rc1.csv", response_curve[:,0], delimiter=",")
    img_rad_map = np.zeros((image_size[0], image_size[1], num_channels),
                           dtype=np.float64)

    for row_idx in xrange(image_size[0]):
        for col_idx in xrange(image_size[1]):
            for channel in xrange(num_channels):
                pixel_vals = np.uint8([images[j][row_idx, col_idx, channel] \
                                      for j in xrange(num_images)])
                weights = np.float64([linearWeight(val) \
                                     for val in pixel_vals])
                sum_weights = np.sum(weights)
                img_rad_map[row_idx, col_idx, channel] = np.sum(weights * \
                    (response_curve[pixel_vals, channel] - log_exposure_times))\
                    / np.sum(weights) if sum_weights > 0.0 else 1.0
    return np.exp(img_rad_map)

def computeImage(map):
    num_channels = map.shape[2]
    image = np.zeros((map.shape[0], map.shape[1], num_channels),
                                  dtype=np.uint8)
    for channel in xrange(num_channels):
        image[:, :, channel] = \
            np.uint8(normalizeImage(map[:, :, channel]))
    return image

def computeLDR(rm):
    #Reinhard Luminance Mapping
    print 'Tone Mapping...'
    num_channels = rm.shape[2]
    #We typically vary a from 0.18 up to 0.36 and 0.72 and vary it down to 0.09, and 0.045.
    a=0.18
    Lw_xy = 0.20 * rm[:,:,0] + 0.72 * rm[:,:,1] + 0.08 * rm[:,:,2]
    N = rm.shape[0] *rm.shape[1]
    saturation = 0.6;
    #The key of a scene indicates whether it is subjectively light, normal, or dark.
    #Log average Luminance
    Lw = np.exp((1/N)*(np.sum(np.log(Lw_xy + .0001))))
    #Scaled Luminance
    L_xy =Lw_xy * (a/Lw)
    #Tone Mapping Operator
    Ld_xy = np.divide(L_xy, (1+L_xy))
    ldr = np.zeros(rm.shape);
    #Reapply colors
    for i in range(num_channels):
        ldr[:,:,i] =np.multiply(np.power( np.divide(rm[:,:,i] ,Lw_xy), saturation), Ld_xy)
    #remove values over 1
    indices = np.where(ldr > 1);
    ldr[indices] = 1
    return ldr

if __name__ == "__main__":
    image_dir = "input"
    output_dir = "output"
    exposure_times = np.float64([1/160.0, 1/125.0, 1/80.0, 1/60.0, 1/40.0,1/15.0])
    #exposure_times = np.float64([1/100.0, 1/100.0, 1/400.0, 1/1600.0, 1/1600.0])
    #exposure_times = np.float64([1/4000.0, 1/1000.0, 1/250.0, 1/60.0, 1/15.0, 1/4.0, 1/1.0, 4.0/1.0, 15.0/1.0])
    #exposure_times=exposure_times[::-1]
    log_exposure_times = np.log(exposure_times)
    np.random.seed()
    rm = computeRM(image_dir, log_exposure_times)
    hdr = computeImage(np.log(rm))
    ldr = computeImage(computeLDR(rm))
    cv2.imwrite(output_dir + "/hdr.jpg", hdr)
    cv2.imwrite(output_dir + "/ldr.jpg", ldr)