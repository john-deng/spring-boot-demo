package cn.vpclub.spring.boot.demo;

import cn.vpclub.spring.boot.demo.service.AClassWithPrivateMethod;
import cn.vpclub.spring.boot.demo.service.AFinalClass;
import cn.vpclub.spring.boot.demo.service.AStaticClass;
import lombok.extern.slf4j.Slf4j;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.testng.Assert;
import org.testng.IObjectFactory;
import org.testng.annotations.ObjectFactory;
import org.testng.annotations.Test;

/**
 *
 * Created by johnd on 23/12/2016.
 */
@PrepareForTest({AFinalClass.class, AStaticClass.class, AClassWithPrivateMethod.class})
@SpringBootTest
@Slf4j
public class PowerMockitoUnitTests {
    private AFinalClass aFinalClass = null;

    @ObjectFactory
    public IObjectFactory getObjectFactory() {
        return new org.powermock.modules.testng.PowerMockObjectFactory();
    }

    @Test(dependsOnMethods = {"testStaticClass2"})
    public void testFinalClass1() {
        final String testInput = "A test input";
        final String mockedResult = "echo from a final class: " + testInput;

        aFinalClass = PowerMockito.mock(AFinalClass.class);
        PowerMockito.when(aFinalClass.echoString(testInput)).thenReturn(mockedResult);

        // Assert the mocked result is returned from method call
        Assert.assertEquals(aFinalClass.echoString(testInput), mockedResult);

        log.info("finished testFinalClass1()");
    }

    @Test(dependsOnMethods = {"testFinalClass1"})
    public void testFinalClass2() {
        final String testInput = "A test input";
        final String mockedResult = "echo from a final class: " + testInput;

        // Assert the mocked result is returned from method call
        Assert.assertEquals(aFinalClass.echoString(testInput), mockedResult);
    }

    @Test
    public void testStaticClass1() {
        PowerMockito.mockStatic(AStaticClass.class);

        final String testInput = "A test input";
        final String mockedResult = "echo from a static class: " + testInput;
        Mockito.when(AStaticClass.echoString(testInput)).thenReturn(mockedResult);

        // Assert the mocked result is returned from method call
        Assert.assertEquals(AStaticClass.echoString(testInput), mockedResult);
    }

    @Test(dependsOnMethods = {"testStaticClass1"})
    public void testStaticClass2() {
        final String testInput = "A test input";
        final String mockedResult = "echo from a static class: " + testInput;

        // Assert the mocked result is returned from method call
        Assert.assertEquals(AStaticClass.echoString(testInput), mockedResult);
    }
}
