/*
 * Copyright 2014 JBoss Inc
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.kie.workbench.common.widgets.metadata.client.widget;

import org.guvnor.common.services.shared.metadata.model.Metadata;
import org.guvnor.common.services.shared.metadata.model.Overview;
import org.junit.Before;
import org.junit.Test;
import org.kie.workbench.common.widgets.client.versionhistory.VersionRecordManager;
import org.mockito.ArgumentCaptor;
import org.uberfire.backend.vfs.ObservablePath;
import org.uberfire.client.callbacks.Callback;
import org.uberfire.client.workbench.type.ClientTypeRegistry;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class OverviewWidgetPresenterTest {

    private OverviewScreenView.Presenter presenter;
    private OverviewScreenView view;
    private OverviewWidgetPresenter editor;

    private Overview overview;

    private VersionRecordManager versionRecordManager;

    @Before
    public void setUp() throws Exception {
        ClientTypeRegistry clientTypeRegistry = mock(ClientTypeRegistry.class);
        view = mock(OverviewScreenView.class);
        versionRecordManager = mock(VersionRecordManager.class);

        editor = new OverviewWidgetPresenter(
                clientTypeRegistry,
                view);
        presenter = editor;

        overview = new Overview();
    }

    @Test
    public void testPresenterSet() throws Exception {
        verify(view).setPresenter(presenter);
    }

    @Test
    public void testAddingDescription() throws Exception {

        Metadata metadata = new Metadata();
        overview.setMetadata(metadata);

        ObservablePath observablePath = mock(ObservablePath.class);
        editor.setContent(overview, observablePath, versionRecordManager.getVersion());

        presenter.onDescriptionEdited("Hello");

        assertEquals(overview.getMetadata().getDescription(), "Hello");
    }

}
