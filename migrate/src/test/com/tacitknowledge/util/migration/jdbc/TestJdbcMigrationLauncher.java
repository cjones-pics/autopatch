/* Copyright 2006 Tacit Knowledge LLC
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

import java.sql.Connection;

import com.tacitknowledge.util.migration.MigrationException;

/**
 * Override select things in the JdbcMigrationLauncher for testing purposes
 * 
 * @author Mike Hardy (mike@tacitknowledge.com)
 */
public class TestJdbcMigrationLauncher extends JdbcMigrationLauncher
{
    /** The PatchTable to use for migrations */
    private PatchTable patchTable = null;
    
    /**
     * Delegates to the superclass
     */
    public TestJdbcMigrationLauncher()
    {
        super();
    }
    
    /**
     * Delegating constructors
     */
    public TestJdbcMigrationLauncher(JdbcMigrationContext context) throws MigrationException
    {
        super(context);
    }
    
    /**
     * Override the patch table creation to be the patch table we have
     * 
     * @return patchTable held internally
     */
    protected PatchTable createPatchTable(Connection conn)
    {
        if (patchTable != null)
        {
            return patchTable;
        }
        
        return super.createPatchTable(conn);
    }
    
    /**
     * Set the PatchTable object to use for migrations
     * 
     * @param patchTable the PatchTable to use
     */
    public void setPatchTable(PatchTable patchTable)
    {
        this.patchTable = patchTable;
    }
}