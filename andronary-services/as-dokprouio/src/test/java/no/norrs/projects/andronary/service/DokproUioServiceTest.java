/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package no.norrs.projects.andronary.service;

import junit.framework.TestCase;
import no.norrs.projects.andronary.service.DokproUioService.Dicts;
import no.norrs.projects.andronary.service.utils.Dictionary;

/**
 *
 * @author rockj
 */
public class DokproUioServiceTest extends TestCase {

    public enum Fake implements no.norrs.projects.andronary.service.utils.Dictionary {

        WRONG_ENUM_CLASS, FOR_TEST_PURPOSES;
    }

    public DokproUioServiceTest(String testName) {
        super(testName);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * Test of selectDictionary method, of class DokproUioService.
     */
    public void testSelectDictionary() {

        System.out.println("selectDictionary");
        DokproUioService instance = new DokproUioService();
        boolean result = instance.selectDictionary(Dicts.nnNO);
        assertEquals(true, result);
        assertEquals(false, instance.selectDictionary(Fake.WRONG_ENUM_CLASS));
    }

    /**
     * Test of getSelectedDictionary method, of class DokproUioService.
     */
    public void testGetSelectedDictionary() {
        System.out.println("getSelectedDictionary");
        DokproUioService instance = new DokproUioService(Dicts.nbNO);
        assertEquals(Dicts.nbNO, instance.getSelectedDictionary());
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of getDictionaries method, of class DokproUioService.
     */
    public void testGetDictionaries() {
        System.out.println("getDictionaries");
        DokproUioService instance = new DokproUioService();

        Dictionary[] result = instance.getDictionaries();

        assertNotNull(result);
        assertEquals(2, result.length);
    }

    /**
     * Test of lookup method, of class DokproUioService.
     */
    public void testLookup() {
        System.out.println("lookup");
        String dict = "";
        DokproUioService instance = new DokproUioService();
        String[] expResult = null;
        //String[] result = instance.lookup(dict);
        //assertEquals(expResult, result);
    }
}
