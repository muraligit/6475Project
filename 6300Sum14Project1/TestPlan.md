# Test Plan - Team 22

## 1. Introduction

The SenLen program will evaluate text documents to determine the average sentence length for that document.  

- It will use the command line interface (CLI) to set parameters for the text file and path to be opened, the characters to be used for delimiters (determined by the client), and the minimum size of word length to be considered.

## 2. Quality Control

- In determining quality control, we need to evaluate sample testing situations from various departments (English, Humanities, Mathematics, and Computer Science), each of which use differing standard delimiters.

### 2.1 - Test Plan Quality

- In the first prototype, we will be testing proof of concept ideas such as reading from a file in a literally declared location, parsing words of known origin, and sentences of known length.  

- In later iterations, we will be testing variable file names, using sample data from an outside source in the various disciplines, and using variable delimiters, ignoring words of variably determined length

- In the final version, we will test from the command line, with user-determined file names, delimiters, and word length, with a testing of the parameter listing, orders, and handling of special cases.

### 2.2 - Adequacy Criterion

- In the first prototype and later iterations, we will be using known input data for which we know the expected value.  

- For later tests the appropriate output will be determined for the variable input.

### 2.3 - Bug Tracking

- During development, bugs and enhancement requests will be tracked by the GitHub communication protocols as well as team communications through chat and e-mail.

- After development, it is assumed bugs found by the users would be brought to the attention of the professor or instructor whose class they are in, which in turn would relay those bugs as well as preferred enhancement requests to the development team.  Recurring user errors should be accommodated for as reasonably as possible, and client errors should be eliminated.

# 3 - Test Strategy

- The prototype phase will involve ensuring that the program can read files correctly from a file, and correctly parse words and sentences for counting, given explicitly defined delimiters.  Simple intermediate output statements should so if file input is properly handled (including spacing, delimiters, new lines, and other whitespace), and counts accurately maintained.

- The intermediate step should test variable file names, paths, delimiters, and word size values to ignore.  Given that the input files and parameters are known, it should be easy to calculate the appropriate response and determine its validity.

- The later steps of testing should ensure that the command line interface works appropriately, and should experiment with text from unknown and random sources.  The output should be determined independently and checked against our program for further refinements.  User error should be handled gracefully and client errors should be as close to zero as possible.

## 3.1 - Testing Process

- 

## 3.2 - Technology

- Simple debugging statements should suffice for the prototype level of testing.  Subsequent levels of testing will involve using JUnit 4 to test typical cases.  As we get close to release, command line interactions should be generated to test typical and atypical user inputs.

# 4 - Test Cases

- Test cases for the Prototype phase:

- Test cases for the JUnit phase:


**yes, I know I need to fix this.**

| File Name | Delimiters | Min. Word Count | Text | Expected Result | Actual Result | P/F |
| essay.txt | “.?!” | 5 | Same text as below | 4  |  |  |
| essay.txt | “.?!” | 3 | ```The essay below demonstrates the principles of writing a basic essay. The
different parts of the essay have been labeled. The thesis statement is in bold,
the topic sentences are in italics, and each main point is underlined. When you
write your own essay, of course, you will not need to mark these parts of the
essay unless your teacher has asked you to do so. They are marked here just so
that you can more easily identify them.

"A dog is man's best friend." That common saying may contain some truth, but
dogs are not the only animal friend whose companionship people enjoy. For many
people, a cat is their best friend. Despite what dog lovers may believe, cats
make excellent housepets as they are good companions, they are civilized members
of the household, and they are easy to care for.

In the first place, people enjoy the companionship of cats. Many cats are
affectionate. They will snuggle up and ask to be petted, or scratched under the
chin. Who can resist a purring cat? If they're not feeling affectionate, cats
are generally quite playful. They love to chase balls and feathers, or just
about anything dangling from a string. They especially enjoy playing when their
owners are participating in the game. Contrary to popular opinion, cats can be
trained. Using rewards and punishments, just like with a dog, a cat can be
trained to avoid unwanted behavior or perform tricks. Cats will even fetch!

In the second place, cats are civilized members of the household. Unlike dogs,
cats do not bark or make other loud noises. Most cats don't even meow very
often. They generally lead a quiet existence. Cats also don't often have
"accidents." Mother cats train their kittens to use the litter box, and most
cats will use it without fail from that time on. Even stray cats usually
understand the concept when shown the box and will use it regularly. Cats do
have claws, and owners must make provision for this. A tall scratching post in a
favorite cat area of the house will often keep the cat content to leave the
furniture alone. As a last resort, of course, cats can be declawed.

Lastly, one of the most attractive features of cats as housepets is their ease
of care. Cats do not have to be walked. They get plenty of exercise in the house
as they play, and they do their business in the litter box. Cleaning a litter
box is a quick, painless procedure. Cats also take care of their own grooming.
Bathing a cat is almost never necessary because under ordinary circumstances
cats clean themselves. Cats are more particular about personal cleanliness than
people are. In addition, cats can be left home alone for a few hours without
fear. Unlike some pets, most cats will not destroy the furnishings when left
alone. They are content to go about their usual activities until their owners
return.

Cats are low maintenance, civilized companions. People who have small living
quarters or less time for pet care should appreciate these characteristics of
cats. However, many people who have plenty of space and time still opt to have a
cat because they love the cat personality. In many ways, cats are the ideal
housepet.
```| 10 |  |  |
| file.txt | “|%” | 3 |```
hello|world%here|we#are| 
```| 1 |  |  |

| multi.txt | “.?!” | 3 |```
This is an example of a sentence that spans
multiple lines in
the text file. We need to handle it.
```| 6 |  |  |

| numbers.txt | “/|” | 1 | ```
1/2,2/3,3,3/4,4,4,4/5,5,5,5,5/6,6,6,6,6,6|7 7 7 7 7 7 7/8 8 8 8,8 8 8 8|9,9,9,9,9,9,9,9,9|10,10,10,10,10,10,10,10,10,10
```| 5  |  |  |

- Test cases for the User/Client phase:

