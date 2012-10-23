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
public class CommandTest {
    public static final Logger LOG = LoggerFactory.getLogger(CommandTest.class);
    private HashMap<String,Object> test;
    private HashMap<String,Object> live;
    
      
    @Before
    public void setup() {
        test = new HashMap<String,Object>();
        live = new HashMap<String,Object>();   
    }
    
    @Test
    public void checkSiblings(){
        AcePathfinderUtil.insertValue(test, "sllb", "30");
        AcePathfinderUtil.insertValue(test, "slll", "0.22");
        AcePathfinderUtil.insertValue(test, "sldul", "0.32");
        AcePathfinderUtil.insertValue(test, "sloc", "0.9");
        AcePathfinderUtil.insertValue(test, "sllb", "50");
        AcePathfinderUtil.insertValue(test, "slll", "0.15");
        AcePathfinderUtil.insertValue(test, "sldul", "0.24");
        AcePathfinderUtil.insertValue(test, "sloc", "0.01");
        
        LOG.debug(Command.traverseAndGetSiblings(test, "slll").toString());
        
    }
}
