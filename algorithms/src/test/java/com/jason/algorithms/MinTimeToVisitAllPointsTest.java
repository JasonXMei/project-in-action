package com.jason.algorithms;

import com.jason.algorithms.array.MinTimeToVisitAllPoints;
import org.junit.Assert;
import org.junit.Test;

public class MinTimeToVisitAllPointsTest {

    @Test
    public void case1(){
        int[][] points = {{1,1},{3,4},{-1,0}};
        Assert.assertEquals(7, MinTimeToVisitAllPoints.method1(points));
    }

    @Test
    public void case2(){
        int[][] points = {{3,2},{-2,2}};
        Assert.assertEquals(5, MinTimeToVisitAllPoints.method1(points));
    }
}
