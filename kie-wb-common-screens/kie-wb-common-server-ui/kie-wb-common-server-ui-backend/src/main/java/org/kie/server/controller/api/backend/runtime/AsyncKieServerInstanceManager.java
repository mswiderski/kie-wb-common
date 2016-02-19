/*
 * Copyright 2016 Red Hat, Inc. and/or its affiliates.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
*/

package org.kie.server.controller.api.backend.runtime;

import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;

import org.kie.server.controller.api.model.runtime.Container;
import org.kie.server.controller.api.model.runtime.ServerInstanceKey;
import org.kie.server.controller.api.model.spec.ContainerSpec;
import org.kie.server.controller.api.model.spec.ServerTemplate;
import org.kie.server.controller.impl.KieServerInstanceManager;
import org.uberfire.commons.async.DisposableExecutor;
import org.uberfire.commons.async.SimpleAsyncExecutorService;

@ApplicationScoped
public class AsyncKieServerInstanceManager extends KieServerInstanceManager {

    private DisposableExecutor executor;

    @PostConstruct
    public void configure() {
        executor = SimpleAsyncExecutorService.getDefaultInstance();
    }

    @Override
    public List<Container> startScanner(final ServerTemplate serverTemplate, final ContainerSpec containerSpec, final long interval) {
        executor.execute( new Runnable() {
            @Override
            public void run() {
                List<Container> containers = AsyncKieServerInstanceManager.super.startScanner(serverTemplate, containerSpec, interval);
            }
        } );
        return null;
    }

    @Override
    public List<Container> stopScanner(final ServerTemplate serverTemplate, final ContainerSpec containerSpec) {
        executor.execute( new Runnable() {
            @Override
            public void run() {
                List<Container> containers = AsyncKieServerInstanceManager.super.stopScanner(serverTemplate, containerSpec);
            }
        } );
        return null;
    }

    @Override
    public List<Container> scanNow(final ServerTemplate serverTemplate, final ContainerSpec containerSpec) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                List<Container> containers = AsyncKieServerInstanceManager.super.scanNow(serverTemplate, containerSpec);
            }
        });
        return null;
    }

    @Override
    public List<Container> startContainer(final ServerTemplate serverTemplate, final ContainerSpec containerSpec) {
        executor.execute( new Runnable() {
            @Override
            public void run() {
                List<Container> containers = AsyncKieServerInstanceManager.super.startContainer(serverTemplate, containerSpec);
            }
        } );
        return null;
    }

    @Override
    public List<Container> stopContainer(final ServerTemplate serverTemplate, final ContainerSpec containerSpec) {
        executor.execute( new Runnable() {
            @Override
            public void run() {
                List<Container> containers = AsyncKieServerInstanceManager.super.stopContainer(serverTemplate, containerSpec);
            }
        } );
        return null;
    }

    @Override
    public List<Container> upgradeContainer(final ServerTemplate serverTemplate, final ContainerSpec containerSpec) {
        executor.execute( new Runnable() {
            @Override
            public void run() {
                List<Container> containers = AsyncKieServerInstanceManager.super.upgradeContainer(serverTemplate, containerSpec);
            }
        } );
        return null;
    }

    @Override
    public List<Container> getContainers(final ServerInstanceKey serverInstanceKey) {
        executor.execute( new Runnable() {
            @Override
            public void run() {
                List<Container> containers = AsyncKieServerInstanceManager.super.getContainers(serverInstanceKey);
            }
        } );
        return null;
    }
}
