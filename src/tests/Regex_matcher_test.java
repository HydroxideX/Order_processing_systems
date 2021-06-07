package tests;

import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.Test;
import utils.regex_matcher;

public class Regex_matcher_test {
    regex_matcher match = new regex_matcher();
    @Test
    public void testDate(){
        String date1 = "2001-12-19";
        String date2 = "12212-15-63";
        String date3 = "15-36-15323";
        assertTrue(match.check_date(date1));
        assertFalse(match.check_date(date2));
        assertFalse(match.check_date(date3));
    }

    @Test
    public void testInt() {
        String int1 = "1524";
        String int2 = "-3625";
        String int3 = "";
        assertTrue(match.check_int(int1));
        assertFalse(match.check_int(int2));
        assertFalse(match.check_int(int3));
    }

    @Test
    public void testFloat() {
        String float1 = "+52236.545";
        String float2 = "-45";
        String float3 = "";
        assertTrue(match.check_float(float1));
        assertFalse(match.check_float(float2));
        assertFalse(match.check_float(float3));
    }

    @Test
    public void testVarchar() {
        String varchar1 = "adasd";
        String varchar2 = "";
        String varchar3 = "sadasdsadaqeqweiujiojkovnkzxklmkpasdmkpsadasdsadsawqeqwesdsdqqwe";
        assertTrue(match.check_varchar(varchar1));
        assertFalse(match.check_varchar(varchar2));
        assertFalse(match.check_varchar(varchar3));
    }

    @Test
    public void testYear() {
        String year1 = "1994";
        String year2 = "12000";
        String year3 = "12";
        assertTrue(match.check_year(year1));
        assertFalse(match.check_year(year2));
        assertFalse(match.check_year(year3));
    }
}