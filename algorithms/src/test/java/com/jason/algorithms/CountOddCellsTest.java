package com.jason.algorithms;

import com.jason.algorithms.array.CountOddCells;
import org.junit.Assert;
import org.junit.Test;

public class CountOddCellsTest {

    @Test
	public void caseOne() {
        int m = 2, n = 3;
        int[][] indices = {{0, 1}, {1, 1}};
        Assert.assertEquals(6, CountOddCells.method(m, n, indices));
    }

    @Test
	public void caseTwo() {
        int m = 2, n = 2;
        int[][] indices = {{1, 1}, {0, 0}};
        Assert.assertEquals(0, CountOddCells.method(m, n, indices));
    }

}
