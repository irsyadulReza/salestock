# salestock
1. (8 points) 
Introduction

As a data-engineer on fashion e-commerce, you want to build a recommendation system. You already have these two prepared tables  : 
    - user preference table
    - product score table

User preference table is already sorted by User ID (uid), and stored in a textfile, tab separated (TSV) with the following column structure :
    - User ID (uid) int : uniquely identifies the user
    - Product ID (pid) int : uniquely identifies the product
    - Score double : score of the likelihood of user interest to a product, and has range -1 to 1.
    - Timestamp int : the Unix timestamp of last score calculation

Each line has a unique uid-pid value

Example user_preference.txt:
============================
12341     2123     0.832     1508733366
12341     2939     0.200     1457261934
21235     2329     -0.800    1448432918
============================

Product score table also stored in a textfile (TSV), and has the following column structure :
    - Product ID int : uniquely identifies the product
    - Product Score int : has range 0 to 1000

Example product_score.txt:
============================
2939     600     
2123     300     
============================

Algorithm 

The algorithm we want you to write a multiplication of the user_preference score with the product_score plus the product_score. Product score can be directly looked-up from product_score, but for the user_preference score, you need to pre-calculate the table, as follows :

Each uid-pid relationship score is calculated in different time, we can't highly trust on old uid-pid score calculation. So each relationship is given a score which is equal to calculated score times the 0.95 to the power of the number of days between when that score is calculated and the current time. So if a relationship has a score of 0.8 and was calculated 2 days ago, then the final score for this relationship is equal to 0.8 * 0.95^2 = 0.722. Note that the very old calculation will be effectively 0.


Example

Here is simple worked example recommendation calculation for U1 user, to test your understanding :

user preference : 
    U1    P1    -0.333  (10 days ago) -> effective score = -0.333 * 0.95^10 = -0.199
    U1    P2    -0.500  (today) -> effective score = -0.500 * 0.95^0 = -0.500
    U1    P3    1.000  30 days ago -> effective score = 1.000 * 0.95^30 = 0.214
    U1    P5    -0.200 (today) -> effective score = -0.200 * 0.95^0 = -0.200

product score :
    P1  750    
    P2  900
    P3  500
    P4  600
    P5  650
    P6  480
    P7  500

Top 5 recommendation result for U1 :

    P3 -> 500 * 0.214 + 500 = 607.000
    P1 -> 750 * -0.199 + 750 = 600.750
    P4 -> 0 + 600 = 600.000
    P5 -> 650 * -0.200 + 650 = 520.000
    P7 -> 0 + 500 = 500.000


Question

You want to build an API server that receives the uid as an input, then outputs 5 top products in order best to worst. How would you write these two functions to optimize the latency for each API call (no need to write an API server, just executable command prompt):

- initialize(user_preference_file, product_score_file)
This function takes 2 string input, which is the path of file inputs. This function doing all precomputing calculation. 
- top_5_product(uid)
This function takes uid arguments, simulate the called whenever the API is called, and return 5 recommendation products.

Example of execution outputs (or you can be creative) :
    $> <program_name> initialize
    $> ---done---
    $> <program_name> recommend-products 12341
    $> 2123
    $> 29
    $> 100
    $> 4504
    $> 8888

Your solution must be scalable so that it can run in a timely fashion dealing with the large data files. Just say, you have to deal with at least a hundred million of user_preferences and ten thousand of products data.

You can use Python or Java or Scala, and again, you are not allowed to use any external library besides the built-in library for the language that you choose.


2. (4 points) You are given a textfile that contains the age (Integer) of the people. The age.txt contains the age of the whole world (7 Billion People). And you only have a shitty laptop with 1GB of RAM. 

Write a script/program in Python or Java or Scala that takes this file name as an input, and produce an output file that contains the sorted age in the same format. You and again, you are not allowed to use any external library besides the built-in library for the language that you choose.

Example age.txt:
============================
23
51
35
============================

Example sorted_age.txt:
============================
23
35
51
============================