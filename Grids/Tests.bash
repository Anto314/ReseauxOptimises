#!/bin/bash
# Do some sudoku resolution to find out what grids can't be solved.
# Author : Adrien RICCIARDI
# Version 0.0.1 : 23/12/2012
PROGRAM="java -ea sudoku.monothreaded.Main"
TEST_PATH="../../Grids"

function PrintFailure
{
	echo "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!"
	echo "!! FAILURE !!"
	echo "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!"
	exit
}

function PrintTotalSuccess
{
	echo "################################################################################"
	echo "## SUCCESS : all tests were successful ##"
	echo "################################################################################"
}

cd ../build/classes

#----------------------------------------------------------------------------------------------------
# Easy grids
#----------------------------------------------------------------------------------------------------
$PROGRAM $TEST_PATH/Easy_1.txt
if [ $? != 0 ]
then
	PrintFailure
fi

$PROGRAM $TEST_PATH/Easy_2.txt
if [ $? != 0 ]
then
	PrintFailure
fi

$PROGRAM $TEST_PATH/Easy_3.txt
if [ $? != 0 ]
then
	PrintFailure
fi

$PROGRAM $TEST_PATH/Easy_4.txt
if [ $? != 0 ]
then
	PrintFailure
fi

#----------------------------------------------------------------------------------------------------
# Medium grids
#----------------------------------------------------------------------------------------------------
$PROGRAM $TEST_PATH/Medium_1.txt
if [ $? != 0 ]
then
	PrintFailure
fi

$PROGRAM $TEST_PATH/Medium_2.txt
if [ $? != 0 ]
then
	PrintFailure
fi

#----------------------------------------------------------------------------------------------------
# Hard grids
#----------------------------------------------------------------------------------------------------
$PROGRAM $TEST_PATH/Hard_1.txt
if [ $? != 0 ]
then
	PrintFailure
fi

#----------------------------------------------------------------------------------------------------
# Hardcore grids
#----------------------------------------------------------------------------------------------------
$PROGRAM $TEST_PATH/Hardcore_1.txt
if [ $? != 0 ]
then
	PrintFailure
fi

$PROGRAM $TEST_PATH/Hardcore_2.txt
if [ $? != 0 ]
then
	PrintFailure
fi

# $PROGRAM $TEST_PATH/Hardcore_3.txt
# if [ $? != 0 ]
# then
# 	PrintFailure
# fi

PrintTotalSuccess
