package org.agmip.util.assume;

import java.util.HashMap;

import org.agmip.ace.util.AcePathfinderUtil;

import org.junit.Before;
import org.junit.Test;
//import static org.junit.Assert.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author frostbytten
 */
public class AssumeTest {
    public static final Logger LOG = LoggerFactory.getLogger(AssumeTest.class);
    private HashMap<String,Object> test;
    private HashMap<String,Object> live;
    
      
    @Before
    public void setup() {
        test = new HashMap<String,Object>();
        live = new HashMap<String,Object>();   
    }
    
    @Test
    public void testStringAsssume() {      
        AcePathfinderUtil.insertValue(test, "exname", "bob");
        Assume.run(live, "exname", new String[] {"bob"});
        
        LOG.info(live.toString());
    }
    
    @Test
    public void testStringNoOverwriteExistingAssume() {      
        AcePathfinderUtil.insertValue(test, "exname", "bob");
        LOG.info("Pre-assume: "+test.toString());
        Assume.run(test, "exname", new String[] {"alice"});
        LOG.info("Post-assume: "+test.toString());
    }
    
    @Test
    public void testVariableAssume() {
        AcePathfinderUtil.insertValue(test, "pdate", "19810101");
        Assume.run(test, "icdat", new String[] {"$PDATE"});
        
        LOG.info(test.toString());
    }
    
}
