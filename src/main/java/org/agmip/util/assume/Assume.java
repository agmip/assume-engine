package org.agmip.util.assume;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;

import org.agmip.ace.util.AcePathfinderUtil;

public class Assume extends Command {
    public static final Logger LOG = LoggerFactory.getLogger(Assume.class);

    public static void run(HashMap m, String var, String[] args) {
        var = var.toLowerCase();
        String rawValue = Command.getRawValue(m, var);
        LOG.debug("FOUND: ["+var+"] "+rawValue);
        if (rawValue.equals("")) {
            // Load the assumptions (INCOMPLETE IMPLEMENTATION)
           if (args[0].startsWith("$")) {
               String replaceValue = Command.getRawValue(m, args[0].substring(1).toLowerCase());
               AcePathfinderUtil.insertValue(m, var, replaceValue);
           } else {
               AcePathfinderUtil.insertValue(m, var, args[0]);
           }
        }
    }
}
