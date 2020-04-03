package com.example.stepaholic;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    private MainActivity mainActivity = new MainActivity();
    @Test
    public void stepCheck() {
        assertEquals(0,mainActivity.getSteps(),0.1);

}
@Test
public void extraStepCheck(){
        mainActivity.setSteps(50);
        assertEquals(50.0,mainActivity.getSteps(),0.1);
}
@Test
    public void userRunningCheck(){
        assertFalse(mainActivity.isUserRunning());
}
@Test
    public void setUserRunningCheck(){
        mainActivity.setUserRunning(true);
        assertTrue(mainActivity.isUserRunning());
}

}
