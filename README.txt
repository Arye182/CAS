--------------------------------------------------------------------------------
------------------------- The Simplificatzius - Ver 1.00 -----------------------
--------------------------------------------------------------------------------
        _____                       _ _  __ _           _       _
       / ____|                     | (_)/ _(_)         | |     (_)
      | (___  _   _ _ __ ___  _ __ | |_| |_ _  ___ __ _| |_ _____ _   _ ___
       \___ \| | | | '_ ` _ \| '_ \| | |  _| |/ __/ _` | __|_  / | | | / __|
       ____) | |_| | | | | | | |_) | | | | | | (_| (_| | |_ / /| | |_| \__ \
      |_____/ \__, |_| |_| |_| .__/|_|_|_| |_|\___\__,_|\__/___|_|\__,_|___/
               __/ |         | |
              |___/          |_|

                    __      __           __     ___    ___
                    \ \    / /          /_ |   / _ \  / _ \
                     \ \  / /___  _ __   | |  | | | || | | |
                      \ \/ // _ \| '__|  | |  | | | || | | |
                       \  /|  __/| |     | | _| |_| || |_| |
                        \/  \___||_|     |_|(_)\___/  \___/

                              _____________________
                             |  _________________  |
                             | | Symplificatzius | |
                             | |_____Ver_1.00____| |
                             |  ___ ___ ___   ___  |
                             | | 7 | 8 | 9 | | + | |
                             | |___|___|___| |___| |
                             | | 4 | 5 | 6 | | - | |
                             | |___|___|___| |___| |
                             | | 1 | 2 | 3 | | x | |
                             | |___|___|___| |___| |
                             | | . | 0 | = | | / | |
                             | |___|___|___| |___| |
                             |_____________________|

--------------------------------------------------------------------------------
Program: "The Simplificatzius"
Version: 1.00
Programmer: Arye182
All Rights Reserved
Release Date: 1.05.2019
--------------------------------- File List ------------------------------------
- Expression.java
- Simplification.java
- BaseExpression.java
- BinaryExpression.java
- UnaryExpression.java
- Plus.java
- Minus.java
- Mult.java
- Div.java
- Pow.java
- Log.java
- Cos.java
- Sin.java
- Neg.java
- Var.java
- Num.java
- ExpressionTest.java
- SimplificationDemo.java
--------------------------------------------------------------------------------
------------------------------- Opening Remarks --------------------------------
--------------------------------------------------------------------------------
Simplifying Algebric Expression is a quit nice and simple action, well, as far
as it a mission that a human being has to solve. in our case, we actually ask
the computer to to that for us!

Approaching this after researching on the internet enough to understand this
issue - simplifying math Expressions becomes a quit challenge.
this problem is so big and large that there is a whole branch of academic and
advanced researches on it. advanced programs like "WolframAlpha" or "Symbolab"
- their teams worked for years to achieve something really advanced.
so i hope you take this under consideration when checking our projects.

In order to build a Computer Algebric System (CAS) or as a matter of fact - a
ReWriting "Machine", we need to work according 4 important principles:
1. If you want a specific simplified form - DEFINE IT.
2. Thing BIG but work in baby steps.
3. Every mathematical Expression can be rewritten as addition or multiplication
   operators Expression.
4. Keep It Simple!
--------------------------------------------------------------------------------
------------------------------------ THE PLAN ----------------------------------
--------------------------------------------------------------------------------
we will create a new interface that called Simplification - it will include new
methods that will help us to create something i call "MECHANISM".
In our mechanism  which is THE - Algorithm of advanced simplification - method
of the first phase will be taken from previous interface (simplify).
every time we want to operate a simplification on an expression we
will call a function that will run all simplification phases on it. the order of
the phases is important and will be as described below.

Important Remark: we might need to repeat the simplification algorithm few times
due to the dependency of steps. in this level I am not willing to think about an
algorithm that will calculate how many iterations of simplification needed since
it is not quit sure yet what is the perfect simplification form that will
satisfy us all. In addition, the amount of iterations sometimes is dependent in
the length of the Expression and amount of simplification activities that has to
be taken to simplify it or as a simple example: how many parenthesis we need to
open using distributivity...

It's all matter of defining what is our goal of simplification and what are the
mathematical actions we want to support.
for example - do we want to support the sum of to rational functions? this will
need an algorithm that will know how to take rational functions and add them
together. or do we want the simplification to support Polynomials Division?
Partial Fractions? - I will keep these 3 nice math actions to our next version.

The Mechanism:
the mechanism is our algorithm to do the simplification. given an expression as
long and complicate as we want - we expect that black box to print out
eventually the most compact version of this expression.
1. simplify and canonical X12 times in a row
   Remark: the reason for 12 times is to cover long parenthesis
   expressions that need more than 2-3 repeats (after checking).
   meaning - there are some actions that has to be done more then once.
2. collecting like terms
3. simplify
4. algebra magic
5. simplify
6. final string (will be implemented at the demo print method.
--------------------------------------------------------------------------------
-------------------------------- Figure 1.0 ------------------------------------
---------------------------- The Mechanism Flow --------------------------------
--------------------------------------------------------------------------------

                    |-------------------------|               |----------------|
                    |    (7)   Algebra        |               | (5) collecting |
                    |           Magic         |               |    LikeTerms   |
                    |-------------------------|               |----------------|
                                |     ^                           |    ^
                                |     |                           |    |
                                |     |    _______________________|    |
                                |     |    |                           |
  |----------------|            |     |    |                           |
  | (1) Expression |            |     |    |                           |
  |----------------|            |     |    |                           |
          ||                    |     |    |                           |
          \/                    \/    |    \/                          |
  |----------------|         |----------------|   12 times!   |----------------|
  | (2) Mechanism  |------->>|(3,6,8)Simplify |<<----------->>| (4)toCanonical |
  |----------------|         |----------------|     (loop)    |----------------|
                                     ||
                                     ||
                                     \/
                        |-------------------------|
                        | (9)   toFinalString     |
                        |-------------------------|
                                     ||
                                     ||
                                     \/
                        |-------------------------|
                        |(10) Advanced Simplified |
                        |        Expression       |
                        |-------------------------|

--------------------------------------------------------------------------------
------------------ SIMPLIFY PHASE 1 - "Basic Simplification" -------------------
--------------------------------------------------------------------------------
the very basic simplification from original mission.
we want to take this basic simplification after every "advanced" one - just
in case we will not miss anything. this simplification supports the following
simplification rules:
- x * 1 = x
- x * 0 = 0
- x + 0 = x
- x / x = 1
- X / 1 = x
- X - 0 = X
- 0 - X = -X
- X - X = 0
- log(x, x) = 1
- an expression without variables evaluates to its result. ((2*8)-6)^2 => 100.
--------------------------------------------------------------------------------
--------------------- SIMPLIFY PHASE 2 - "To Canonical" ------------------------
--------------------------------------------------------------------------------
In computer science, and more specifically in computer algebra, when
representing mathematical objects in a computer, there are usually many
different ways to represent the same object. In this context, a canonical
form is a representation such that every object has a unique representation.
Thus, the equality of two objects can easily be tested by testing the equality
of their canonical forms. (Wikipedia, "Canonical Form").

We want to convert our expression to it's canonical form. this is the longest
part - and quit weird that we take our expression and turn it into more
complexer and longer one instead of simplify it to a short one.

but no worries - all this hard work is planned such that will give us our fruits
in the future.

in order to manage a simplification system - an elementary principle is to have
a list of rules. the rules which will be described are implemented in this
method "toCanonical" and their job is to define in each expression type
(Plus, Minus, Neg, etc.) what action has to be taken and under what conditions.
in this part we will use strongly the help of Mr. "instanceof".

ToCanonical method:
this is Phase 2 in the algorithm which i call "Mechanism". this phase is maybe
the most important one in the process. in this phase the target is to bring the
Expression to it's Canonical form.
The main idea is to use the following math principles to achieve our goal:
1. Commutativity.
2. Distributivity.
3. Associativity.
for example if we have the expression: ((x * 5) + x) * 2
we expect to get this result after the "toCanonical" phase:
2*x^1 + 10*x^1.
of course we use any other math and algebra rules in order to simplify in
a more advanced way (powers, logs etc.).

Down below is a list of rules that all of their purpose is to do this conversion
process.

--------------------------------------------------------------------------------
----------------------------- LIST - OF - RULES --------------------------------
--------------------------------------------------------------------------------
Negation (Neg):
1. turns into multiplication: -(x) => -1 * x

Subtraction (Minus):
1. turns into Addition: x - 10 => x + (-10).

Addition (Plus):
1. ((Num + Exp) + Num) => (Num + Num) + Exp
2. same as 1 but Num on left.

Division (Div):
1. turns into multiplication if divisor is a number multiple on the reciprocal.
2. (a / b) / (c / d) => (a * d) / (c * b)

Multiplication - strong use of Distributivity (Mult):
1. Exp * Num => Num * Exp (to keep order of this kind of expressions)
2. (Num1 * (Num2 * Exp)) => (Num1 * Num2) * Exp.
3. (Num1 + Exp) * Num2 = > (Num1 * Num2) + (Num2 * Exp).
4. same as 3 just Num2 is on left.
5. (Exp1 - Exp2) * Num => (Num * Exp1) - (Num * Exp2).
6. same as 5 but Num on left.
7. same for Exp instead of Num.

Variables (Var):
1. x => 1 * x^1

Powers (Pow):
1. x => x ^ 1
2. a^m * b^m => (ab)^m
3. a^m * a^n => a^(m + n)
4. a^m / a^n => a^(m - n)
5. (a^m)^n = a^(m * n)
6. 1 / a^m => a^(-1)

Logs (Log):
1. a^Log(a, x) => x
2. Log(a, x) + Log(a, y) => Log(a, (x*y))
3. Log(a, x) - Log(a, y) => Log(a, (x/y))
4. Log(a, x^n) => n * (Log(a, x))

Trigonometric (Sin, Cos):
2. sin(-x) => -sin(x)
3. cos(-x) => cos(x)

Num -> returns itself.

--------------------------------------------------------------------------------
------------------ SIMPLIFY PHASE 3 - "Collecting Like Terms" ------------------
--------------------------------------------------------------------------------
Collecting like terms method:
this method's purpose is to combine together expressions that can be
folded together. like 2x + 5x => 7x.
in order to achieve that I chose to shift the expression to another data
structure - a list of expressions. I find it more convenient to iterate
a list than the recursive tree.
the steps are:
 1. Taking the expression and put it in a list (of Expressions).
 2. Iterate the list with loop inside a loop to compare every element with
    any other element of the expression.
 3. If a combine can be done it puts null instead of the two elements and
    create a new element in the list.
 4. Kicking out all the nulls
 5. Switch back to the expression data structure of a tree.

important REMARK: the assumption is that all the expressions are related
by addition as a result from the canonical process. in division the
assumption that we have multiplication.
in this version we dont support rational functions addition yet.

--------------------------------------------------------------------------------
---------------------- SIMPLIFY PHASE 4 - return phase 1 -----------------------
--------------------------------------------------------------------------------
-------------------- SIMPLIFY PHASE 5 - "Algebric Formulas" --------------------
--------------------------------------------------------------------------------
here i will try to implement al kind of algebra or math known formulas of
simplification - this is my wishful thinking - i am declaring here that in this
version - not all of this will be implemented, we want our customer to be hungry
for more (yet we need our strategy) - so see Next Version (2.00) comments below.
--------------------------------- Ver. 1.00 ------------------------------------
Power:
------
1. x^1 = x ; this is not something so impressive but the reason it is here is to
   clean up the "left-overs" from previous phase. now when we do not need the
   canonical form anymore.
2. a2 + 2ab + b2 => (a + b)^2

Trigonometric:
--------------
1. (sin(x))^2 + (cos(x))^2 => 1
2. sin(a)cos(b) + sin(b)cos(a) => sin(a + b)
3. sin(a)cos(b) - sin(b)cos(a) => sin(a - b)
4. cos(a)cos(b) - sin(a)sin(b) => cos(a + b)
5. cos(a)cos(b) + sin(a)sin(b) => cos(a - b)
6. 2sin(a)cos(a) => sin(2a)
7. (cos(x))^2 - (sin(x))^2 => cos(2x)
--------------------------------------------------------------------------------
----------------------- SIMPLIFY PHASE 6 - return phase 1 ----------------------
--------------------------------------------------------------------------------
-------------------- SIMPLIFY PHASE 7 - "Its the Final String" -----------------
--------------------------------------------------------------------------------
this part mainly will take care of unwanted parenthesis. and operators.
For Examples:
- (5 * x) => 5x
- (5 + (5*x)) => 5 + 5x
--------------------------------------------------------------------------------

                   __      __           ___      ___    ___
                   \ \    / /          |__ \    / _ \  / _ \
                    \ \  / /___  _ __     ) |  | | | || | | |
                     \ \/ // _ \| '__|   / /   | | | || | | |
                      \  /|  __/| |     / /_  _| |_| || |_| |
                       \/  \___||_|    |____|(_)\___/  \___/

--------------------------------------------------------------------------------
---------------------------------- Next Version --------------------------------
--------------------------------------------------------------------------------
here is a list of ideas to keep and improve this nice program of simplification,
i would love to sit and implement all this but unfortunately i have lots of
other tasks to finish. so i am writing this so you can see that i thought
about this things and improvements.
like in real life - good things come in small pieces so what we call the blocks
of the program or more likely to be a next version of this beautiful app.
---------------------------------- Ver.2.0 -------------------------------------
- partial fractions support
- polynomial divisor
- rational functions addition (GCD etc.)
- trigonometric - sum of functions
- trigonometric - product of functions
- trigonometric - powers of functions
- Sin / Cos => tan (new class of tan)
- a^2 + b^2 = (a - b)^2 + 2ab
- (a - b)^2 = a2 - 2ab + b2
- (a + b + c)^2 = a^2 + b^2 + c^2 + 2ab + 2ac + 2bc
- (a - b - c)^2 = a^2 + b^2 + c^2 - 2ab - 2ac + 2bc
- (a + b)^3 = a^3 + 3a2b + 3ab2 + b^3 ; (a + b)^3 = a^3 + b^3 + 3ab(a + b)
- (a - b)^3 = a^3 - 3a^2b + 3ab^2 - b^3
- a^3 - b^3 = (a - b)(a^2 + ab + b^2)
- a^3 + b^3 = (a + b)(a^2 - ab + b^2)
- (a + b)^3 = a^3 + 3a^2b + 3ab^2 + b^3
- (a - b)^3 = a^3 - 3a^2b + 3ab^2 - b^3
- (a + b)^4 = a^4 + 4a^3b + 6a^2b^2 + 4ab^3 + b^4)
- (a - b)^4 = a^4 - 4a^3b + 6a^2b^2 - 4ab^3 + b^4)
- a^4 - b^4 = (a - b)(a + b)(a^2 + b^2)
- a^5 - b^5 = (a - b)(a^4 + a^3b + a^2b^2 + ab^3 + b^4)
- and a lot of more cool simplifications we can do and improve our algorithm
  all according to the customer request and how much money he will pay :P...
--------------------------------------------------------------------------------
------------------------------------- END --------------------------------------
--------------------------------------------------------------------------------
Thank you very much for reading this, i spent a lot of time writing, examining
this and i learned a lot during this project.
I am really happy that I chose to do it - first of all I really enjoyed. second
I actually felt like im doing something on my own with no guidance and free hand
to do whatever I want - so thanks for that.
maybe it is not like Wolfram Legendary Alpha but I think I managed to create
something satisfying in a level that suitable for us now.
--------------------------------------------------------------------------------

                 _____ _                 _     __   __         
                |_   _| |               | |    \ \ / /          
                  | | | |__   __ _ _ __ | | __  \ V /___  _   _ 
                  | | | '_ \ / _` | '_ \| |/ /   \ // _ \| | | |
                  | | | | | | (_| | | | |   <    | | (_) | |_| |
                  \_/ |_| |_|\__,_|_| |_|_|\_\   \_/\___/ \__,_|


                              _.._   _..---.
                           .-"    ;-"       \
                          /      /           |
                         |      |       _=   |
                         ;   _.-'\__.-')     |
                          `-'      |   |    ;
                                   |  /;   /      _,
                                 .-.;.-=-./-""-.-` _`
                                /   |     \     \-` `,
                               |    |      |     |
                               |____|______|     |
                                \0 / \0   /      /
                             .--.-""-.`--'     .'
                            (#   )          ,  \
                            ('--'          /\`  \
                             \       ,,  .'      \
                              `-._    _.'\        \
                                  `""`    \        \

--------------------------------------------------------------------------------

