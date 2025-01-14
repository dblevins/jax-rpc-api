/*
 * Copyright (c) 2003, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0, which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * This Source Code may also be made available under the following Secondary
 * Licenses when the conditions for such availability set forth in the
 * Eclipse Public License v. 2.0 are satisfied: GNU General Public License,
 * version 2 with the GNU Classpath Exception, which is available at
 * https://www.gnu.org/software/classpath/license.html.
 *
 * SPDX-License-Identifier: EPL-2.0 OR GPL-2.0 WITH Classpath-exception-2.0
 */

package javax.xml.rpc;

import javax.xml.namespace.QName;
import java.net.URL;
import java.util.Iterator;
import javax.xml.rpc.encoding.TypeMappingRegistry;
import javax.xml.rpc.handler.HandlerRegistry;

/** <code>Service</code> class acts as a factory of the following:
 *  <UL>
 *  <LI>Dynamic proxy for the target service endpoint.
 *  <LI>Instance of the type <code>javax.xml.rpc.Call</code> for 
 *      the dynamic invocation of a remote operation on the 
 *      target service endpoint. 
 *  <LI>Instance of a generated stub class
 *  </UL>
 * 
 *  @version 1.0
 *  @author  Rahul Sharma
 *  @see javax.xml.rpc.Call
 *  @see javax.xml.rpc.Stub
**/

public interface Service {

  /** The getPort method returns either an instance of a generated 
   *  stub implementation class or a dynamic proxy. A service client
   *  uses this dynamic proxy to invoke operations on the target 
   *  service endpoint. The <code>serviceEndpointInterface</code> 
   *  specifies the service endpoint interface that is supported by
   *  the created dynamic proxy or stub instance. 
   *
   *  @param portName  Qualified name of the service endpoint in 
   *                   the WSDL service description
   *  @param serviceEndpointInterface Service endpoint interface 
   *                   supported by the dynamic proxy or stub
   *                   instance
   *  @return java.rmi.Remote Stub instance or dynamic proxy that 
   *                   supports the specified service endpoint 
   *                   interface
   *  @throws ServiceException This exception is thrown in the
   *                   following cases:
   *                   <UL>
   *                   <LI>If there is an error in creation of 
   *                       the dynamic proxy or stub instance
   *                   <LI>If there is any missing WSDL metadata
   *                       as required by this method
   *                   <LI>Optionally, if an illegal 
   *                       <code>serviceEndpointInterface</code>
   *                       or <code>portName</code> is specified
   *                   </UL>  
   *  @see java.lang.reflect.Proxy
   *  @see java.lang.reflect.InvocationHandler
  **/			  
  public java.rmi.Remote getPort(QName portName,
		                 Class serviceEndpointInterface) 
                       throws ServiceException;

  /** The getPort method returns either an instance of a generated 
   *  stub implementation class or a dynamic proxy. The parameter 
   *  <code>serviceEndpointInterface</code> specifies the service 
   *  endpoint interface that is supported by the returned stub or
   *  proxy. In the implementation of this method, the JAX-RPC 
   *  runtime system takes the responsibility of selecting a protocol
   *  binding (and a port) and configuring the stub accordingly. 
   *  The returned <code>Stub</code> instance should not be 
   *  reconfigured by the client.
   *
   *  @param serviceEndpointInterface Service endpoint interface
   *  @return Stub instance or dynamic proxy that supports the 
   *                   specified service endpoint interface
   *  @throws ServiceException
   *                   <UL>
   *                   <LI>If there is an error during creation
   *                       of stub instance or dynamic proxy
   *                   <LI>If there is any missing WSDL metadata
   *                       as required by this method
   *                   <LI>Optionally, if an illegal 
   *                       <code>serviceEndpointInterface</code>
   *                       is specified
   *                   </UL>  
  **/
  public java.rmi.Remote getPort(Class serviceEndpointInterface)
                       throws ServiceException;

  /** Gets an array of preconfigured <code>Call</code> objects for 
   *  invoking operations on the specified port. There is one 
   *  <code>Call</code> object per operation that can be invoked 
   *  on the specified port. Each <code>Call</code> object is 
   *  pre-configured and does not need to be configured using 
   *  the setter methods on <code>Call</code> interface.
   *
   *  <p>Each invocation of the <code>getCalls</code> method 
   *  returns a new array of preconfigured <code>Call</code>
   *  objects
   * 
   *  <p>This method requires the <code>Service</code> implementation
   *  class to have access to the WSDL related metadata.
   *
   *  @param portName  Qualified name for the target service endpoint
   *  @return Call[]  Array of pre-configured Call objects
   *  @throws ServiceException If this Service class does not
   *                   have access to the required WSDL metadata
   *                   or if an illegal <code>portName</code> is
   *                   specified.
  **/ 
  public Call[] getCalls(QName portName) throws ServiceException;

  /** Creates a <code>Call</code> instance.
   *  @param portName  Qualified name for the target service endpoint
   *  @return Call instance
   *  @throws ServiceException If any error in the creation of
   *                   the <code>Call</code> object
   **/
  public Call createCall(QName portName) throws ServiceException;

  /** Creates a <code>Call</code> instance.
   *  @param portName  Qualified name for the target service 
   *                   endpoint
   *  @param operationName  Qualified Name of the operation for 
   *                   which this <code>Call</code> object is to 
   *                   be created.
   *  @return Call instance
   *  @throws ServiceException If any error in the creation of
   *                   the <code>Call</code> object
  **/
  public Call createCall(QName portName, QName operationName)
                       throws ServiceException;

  /** Creates a <code>Call</code> instance.
   *  @param portName  Qualified name for the target service 
   *                   endpoint
   *  @param operationName Name of the operation for which this
   *                       <code>Call</code> object is to be 
   *                       created.
   *  @return Call instance
   *  @throws ServiceException If any error in the creation of
   *                   the <code>Call</code> object
  **/
  public Call createCall(QName portName, String operationName)
                       throws ServiceException;


  /** Creates a <code>Call</code> object not associated with 
   *  specific operation or target service endpoint. This 
   *  <code>Call</code> object needs to be configured using the 
   *  setter methods on the <code>Call</code> interface.
   *
   *  @return Call object
   *  @throws ServiceException If any error in the creation of
   *                   the <code>Call</code> object
   */
  public Call createCall() throws ServiceException;

  /** Gets the name of this service.
   *  @return Qualified name of this service
  **/
  public QName getServiceName();

  /** Returns an <code>Iterator</code> for the list of 
   *  <code>QName</code>s of service endpoints grouped by this
   *  service
   *
   *  @return Returns <code>java.util.Iterator</code> with elements
   *          of type <code>javax.xml.namespace.QName</code>
   *  @throws ServiceException If this Service class does not
   *          have access to the required WSDL metadata  
  **/		
  public Iterator getPorts() throws ServiceException;

  /** Gets the location of the WSDL document for this Service.
   *
   *  @return URL for the location of the WSDL document for 
   *          this service
  **/
  public java.net.URL getWSDLDocumentLocation();

  /** Gets the <code>TypeMappingRegistry</code> for this 
   *  <code>Service</code> object. The returned 
   *  <code>TypeMappingRegistry</code> instance is pre-configured 
   *  to support the standard type mapping between XML and Java 
   *  types types as required by the JAX-RPC specification. 
   *
   *  @return The TypeMappingRegistry for this Service object.
   *  @throws java.lang.UnsupportedOperationException
   *          if the <code>Service</code> class does not support
   *          the configuration of {@link TypeMappingRegistry}.
  **/
  public TypeMappingRegistry getTypeMappingRegistry();


  /** Returns the configured <code>HandlerRegistry</code> instance
   *  for this <code>Service</code> instance.
   *  @return HandlerRegistry
   *  @throws java.lang.UnsupportedOperationException
   *          if the <code>Service</code> class does not support 
   *          the configuration of a {@link HandlerRegistry}
  **/
  public HandlerRegistry getHandlerRegistry();
}
