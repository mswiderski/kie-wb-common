package org.kie.workbench.common.widgets.client.menu;

import javax.enterprise.event.Event;
import javax.inject.Inject;

import org.guvnor.common.services.shared.version.VersionService;
import org.guvnor.common.services.shared.version.events.RestoreEvent;
import org.guvnor.structure.client.file.CommandWithCommitMessage;
import org.guvnor.structure.client.file.SaveOperationService;
import org.jboss.errai.common.client.api.Caller;
import org.jboss.errai.common.client.api.RemoteCallback;
import org.kie.workbench.common.widgets.client.resources.i18n.CommonConstants;
import org.uberfire.backend.vfs.Path;
import org.uberfire.ext.widgets.common.client.callbacks.HasBusyIndicatorDefaultErrorCallback;
import org.uberfire.ext.widgets.common.client.common.BusyIndicatorView;
import org.uberfire.mvp.Command;

public class RestoreVersionCommandProvider {

    @Inject
    private Caller<VersionService> versionService;

    @Inject
    private Event<RestoreEvent> restoreEvent;

    @Inject
    private BusyIndicatorView busyIndicatorView;

    public Command getCommand( final Path path ) {
        return new Command() {
            @Override
            public void execute() {
                new SaveOperationService().save( path,
                                                 new CommandWithCommitMessage() {
                                                     @Override
                                                     public void execute( final String comment ) {
                                                         busyIndicatorView.showBusyIndicator( CommonConstants.INSTANCE.Restoring() );
                                                         versionService.call( getRestorationSuccessCallback(),
                                                                              new HasBusyIndicatorDefaultErrorCallback( busyIndicatorView ) ).restore( path, comment );
                                                     }
                                                 } );
            }
        };
    }

    private RemoteCallback<Path> getRestorationSuccessCallback() {
        return new RemoteCallback<Path>() {
            @Override
            public void callback( final Path restored ) {
                //TODO {porcelli} close current?
//                busyIndicatorView.hideBusyIndicator();
//                restoreEvent.fire( new RestoreEvent( restored ) );
            }
        };
    }

}
