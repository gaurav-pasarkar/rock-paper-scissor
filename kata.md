# Iteration 1
Looking at the code in the first glance, these are the major problems
> `All the code sits inside a main method`

_A simplest solution would be to move all the code to a public method_

> `Random choices`

_The program uses `(int)(Math.random()*3);` to simulate the choices of each player. This does not allow us to write any tests. 
Let us control this using DI. 
We have added a `ChoiceGenerator` interface with just 1 implementation `RandomChoiceGenerator` which is exactly the same as `Player.playerChoice`. This now
allows us to add a `FixedChoice` implementation for our test_

> The program writes everything to `stdout`.

_Instead of touching the core logic, start returning the `stdout`. So the `void` method will now return `List<String>` which can be easily asserted_

> The Game declares the first player winning 3 games as the winner. This constant value `3` is buried inside the codebase. 

_Inject the `GAME_WON` deciding factor. This will enable us to write tests in a more controlled fashion. Also we can default it to `3`_

> Values for Rock, Paper and Scissors are not constants.

_Move them to an enum_