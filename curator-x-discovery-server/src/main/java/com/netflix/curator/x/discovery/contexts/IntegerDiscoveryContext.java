/*
 *
 *  Copyright 2011 Netflix, Inc.
 *
 *     Licensed under the Apache License, Version 2.0 (the "License");
 *     you may not use this file except in compliance with the License.
 *     You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *     Unless required by applicable law or agreed to in writing, software
 *     distributed under the License is distributed on an "AS IS" BASIS,
 *     WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *     See the License for the specific language governing permissions and
 *     limitations under the License.
 *  
 */

package com.netflix.curator.x.discovery.contexts;

import com.netflix.curator.x.discovery.ServiceDiscovery;
import com.netflix.curator.x.discovery.rest.DiscoveryContext;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.node.ObjectNode;
import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;

/**
 * For convenience, a version of {@link DiscoveryContext} that uses an int as the
 * payload
 */
@Provider
public class IntegerDiscoveryContext implements DiscoveryContext<Integer>, ContextResolver<DiscoveryContext<Integer>>
{
    private final ServiceDiscovery<Integer> serviceDiscovery;
    private final int instanceRefreshMs;

    public IntegerDiscoveryContext(ServiceDiscovery<Integer> serviceDiscovery, int instanceRefreshMs)
    {
        this.serviceDiscovery = serviceDiscovery;
        this.instanceRefreshMs = instanceRefreshMs;
    }

    @Override
    public int getInstanceRefreshMs()
    {
        return instanceRefreshMs;
    }

    @Override
    public ServiceDiscovery<Integer> getServiceDiscovery()
    {
        return serviceDiscovery;
    }

    @Override
    public void marshallJson(ObjectNode node, String fieldName, Integer payload) throws Exception
    {
        if ( payload != null )
        {
            node.put(fieldName, payload.toString());
        }
    }

    @Override
    public Integer unMarshallJson(JsonNode node) throws Exception
    {
        if ( node != null )
        {
            return Integer.parseInt(node.asText());
        }
        return null;
    }

    @Override
    public DiscoveryContext<Integer> getContext(Class<?> type)
    {
        return this;
    }
}