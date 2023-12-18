## Intro
This command-line game was inspired by the Flipping The Matrix algorithm's problem. While understanding the problem,
I felt a similarity with solving a rubik's cube. I decided to write a game that has different difficulty levels and
scores the player based on timing and number of row/col flips. I chose Java for practice.

## Getting Started
Make sure Java is installed. From the root directory, `cd src && java Main`.

## Goal
The matrix board has 2N * 2N elements. A corner sum is calculated by adding all the
elements in the first N rows and N columns. This can also be visualized as the top left
N x N corner of the matrix board. The goal is to manipulate the matrix's rows and columns
to reach the max possible corner sum. This target sum is shown on each turn.

## Instructions 
The player can flip any row or column any number of times:

- To flip ROWs, type 'r' followed by the row number(s).
- To flip COLs, type 'c' followed by the column number(s).

Examples:

- r123
  - flip rows 1, 2, and 3
- c24
  - flip cols 2 and 4
- r2c34r2
  - flip row 2 then
  - flip col 3 and 4 then
  - flip row 2
