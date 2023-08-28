package com.demo.happid_demo;

import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.*;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;

import com.demo.happid_demo.view.RequestOtp;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class UnitTest {
    @Rule
    public InstantTaskExecutorRule instantExecutorRule = new InstantTaskExecutorRule();

   /* @Test
    public void requestOtpLogic() {
        RequestOtp requestOtp = new RequestOtp();
        String phoneNumber = "1234567890";
        String actual = requestOtp.get_otp(phoneNumber);
        assertEquals("1290",actual);

    }*/
}