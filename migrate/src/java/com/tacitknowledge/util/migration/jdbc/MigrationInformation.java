/* Copyright 2005 Tacit Knowledge LLC
 * 
 * Licensed under the Tacit Knowledge Open License, Version 1.0 (the "License");
 * you may not use this file except in compliance with the License. You may
 * obtain a copy of the License at http://www.tacitknowledge.com/licenses-1.0.
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.tacitknowledge.util.migration.jdbc;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Launches the migration process as a standalone application.  
 * <p>
 * This class expects the following Java environment parameters:
 * <ul>
 *    <li>migration.systemname - the name of the logical system being migrated</li>
 * </ul>
 * <p>
 * Below is an example of how this class can be configured in an Ant build.xml file:
 * <pre>
 *   ...
 *  &lt;target name="patch.information" description="Prints out information about patch levels"&gt;
 *   &lt;java 
 *       fork="true"
 *       classpathref="patch.classpath" 
 *       failonerror="true" 
 *       classname="com.tacitknowledge.util.migration.jdbc.MigrationInformation"&gt;
 *     &lt;sysproperty key="migration.systemname" value="${application.name}"/&gt;
 *   &lt;/java&gt;
 * &lt;/target&gt;
 *   ...
 * </pre> 
 * 
 * @author  Mike Hardy (mike@tacitknowledge.com)
 * @version $Id$
 * @see     com.tacitknowledge.util.migration.MigrationProcess
 */
public class MigrationInformation
{
    /**
     * Class logger
     */
    private static Log log = LogFactory.getLog(MigrationInformation.class);
    
    /**
     * Private constructor - this object shouldn't be instantiated
     */
    private MigrationInformation()
    { 
        // does nothing
    }
    
    /**
     * Get the migration level information for the given system name
     *
     * @param arguments the command line arguments, if any (none are used)
     * @exception Exception if anything goes wrong
     */
    public static void main(String[] arguments) throws Exception
    {
        MigrationInformation info = new MigrationInformation();
        info.getMigrationInformation(System.getProperty("migration.systemname"));
    }
    
    /**
     * Get the migration level information for the given system name
     * 
     * @param systemName the name of the system
     * @throws Exception if anything goes wrong
     */
    public void getMigrationInformation(String systemName) throws Exception
    {
        if (systemName == null)
        {
            throw new IllegalArgumentException("The migration.systemname "
                + "system property is required");
        }
        
        // The MigrationLauncher is responsible for handling the interaction
        // between the PatchTable and the underlying MigrationTasks; as each
        // task is executed, the patch level is incremented, etc.
        try
        {
            JdbcMigrationLauncherFactory launcherFactory = new JdbcMigrationLauncherFactory();
            JdbcMigrationLauncher launcher
                = launcherFactory.createMigrationLauncher(systemName);
            log.info("Current Database patch level is        : "
                + launcher.getDatabasePatchLevel());
            int unappliedPatches = launcher.getNextPatchLevel()
                - launcher.getDatabasePatchLevel() - 1;
            log.info("Current number of unapplied patches is : " + unappliedPatches); 
            log.info("The next patch to author should be     : " + launcher.getNextPatchLevel());
        }
        catch (Exception e)
        {
            log.error(e);
            throw e;
        }
    }
}