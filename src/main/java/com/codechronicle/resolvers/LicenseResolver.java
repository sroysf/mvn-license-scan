package com.codechronicle.resolvers;

import java.util.List;

import com.codechronicle.model.License;
import com.codechronicle.model.MavenCoordinate;

/**
 * Use this interface to define license resolvers. License resolvers are responsible for determining
 * the licenses for a maven artifact based on maven coordinates.
 * 
 * @author SRoy
 *
 */
public interface LicenseResolver {
	
	List<License> getLicenses(MavenCoordinate mvnCoordinate); 
}
