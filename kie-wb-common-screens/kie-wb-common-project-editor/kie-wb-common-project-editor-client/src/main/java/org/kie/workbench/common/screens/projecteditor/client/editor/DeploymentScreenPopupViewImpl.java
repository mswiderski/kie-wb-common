/*
 * Copyright 2015 Red Hat, Inc. and/or its affiliates.
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

package org.kie.workbench.common.screens.projecteditor.client.editor;

import javax.inject.Inject;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.Widget;
import org.gwtbootstrap3.client.ui.CheckBox;
import org.gwtbootstrap3.client.ui.FormGroup;
import org.gwtbootstrap3.client.ui.FormLabel;
import org.gwtbootstrap3.client.ui.HelpBlock;
import org.gwtbootstrap3.client.ui.ModalBody;
import org.gwtbootstrap3.client.ui.TextBox;
import org.gwtbootstrap3.client.ui.constants.ModalBackdrop;
import org.gwtbootstrap3.client.ui.constants.ValidationState;
import org.gwtbootstrap3.extras.select.client.ui.Option;
import org.gwtbootstrap3.extras.select.client.ui.Select;
import org.jboss.errai.security.shared.api.identity.User;
import org.kie.server.controller.api.model.spec.ServerTemplate;
import org.kie.workbench.common.screens.projecteditor.client.resources.ProjectEditorResources;
import org.uberfire.ext.widgets.common.client.common.popups.BaseModal;
import org.uberfire.ext.widgets.common.client.common.popups.footers.ModalFooterOKCancelButtons;
import org.uberfire.ext.widgets.core.client.resources.i18n.CoreConstants;

public class DeploymentScreenPopupViewImpl extends BaseModal {

    interface DeploymentScreenPopupWidgetBinder
            extends
            UiBinder<Widget, DeploymentScreenPopupViewImpl> {

    }

    private DeploymentScreenPopupWidgetBinder uiBinder = GWT.create( DeploymentScreenPopupWidgetBinder.class );

    @Inject
    private User identity;

    @UiField
    FormGroup containerIdTextGroup;

    @UiField
    TextBox containerIdText;

    @UiField
    HelpBlock containerIdTextHelpInline;

    @UiField
    FormLabel serverTemplateLabel;

    @UiField
    FormGroup serverTemplateGroup;

    @UiField
    Select serverTemplateDropdown;

    @UiField
    HelpBlock serverTemplateHelpInline;

    @UiField
    public CheckBox startContainerCheck;

    @UiField
    public FormGroup startContainerRow;

    private Command callbackCommand;

    private final Command okCommand = new Command() {
        @Override
        public void execute() {

            if ( isEmpty( containerIdText.getText() ) ) {
                containerIdTextGroup.setValidationState( ValidationState.ERROR );
                containerIdTextHelpInline.setText( ProjectEditorResources.CONSTANTS.FieldMandatory0( "ContainerId" ) );

                return;
            }

            if ( isEmpty(serverTemplateDropdown.getValue()) ) {
                serverTemplateGroup.setValidationState(ValidationState.ERROR);
                serverTemplateHelpInline.setText(ProjectEditorResources.CONSTANTS.FieldMandatory0( "Server template" ));

                return;
            }

            if ( callbackCommand != null ) {
                callbackCommand.execute();
            }
            hide();
        }

        private boolean isEmpty( String value ) {
            if ( value == null || value.isEmpty() ) {
                return true;
            }

            return false;
        }
    };

    private final Command cancelCommand = new Command() {
        @Override
        public void execute() {
            hide();
        }
    };

    private final ModalFooterOKCancelButtons footer = new ModalFooterOKCancelButtons( okCommand, cancelCommand );

    public DeploymentScreenPopupViewImpl() {
        setTitle( ProjectEditorResources.CONSTANTS.BuildAndDeploy() );
        setDataBackdrop( ModalBackdrop.STATIC );
        setDataKeyboard( true );
        setFade( true );
        setRemoveOnHide( true );

        add( new ModalBody() {{
            add( uiBinder.createAndBindUi( DeploymentScreenPopupViewImpl.this ) );
        }} );
        add( footer );

        addServerTemplateSelectEntry();
    }

    public void configure( Command command ) {
        this.callbackCommand = command;
    }

    public void addServerTemplateSelectEntry() {
        final Option option = new Option();
        option.setText( CoreConstants.INSTANCE.SelectEntry() );
        serverTemplateDropdown.add(option);
        serverTemplateDropdown.refresh();
    }

    public void addServerTemplate( final ServerTemplate serverTemplate ) {
        final String text = serverTemplate.getName();
        final String value = serverTemplate.getName();
        final Option option = new Option();
        option.setText( text );
        option.setValue( value );
        serverTemplateDropdown.add(option);
        serverTemplateDropdown.refresh();
    }

    public String getContainerId() {
        return this.containerIdText.getText();
    }

    public String getServerTemplate() {
        return this.serverTemplateDropdown.getValue();
    }

    public boolean getStartContainer() {
        return startContainerCheck.getValue();
    }

}
