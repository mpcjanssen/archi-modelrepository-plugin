/**
 * This program and the accompanying materials
 * are made available under the terms of the License
 * which accompanies this distribution in the file LICENSE.txt
 */
package org.archicontribs.modelrepository.dialogs;

import org.archicontribs.modelrepository.IModelRepositoryImages;
import org.archicontribs.modelrepository.ModelRepositoryPlugin;
import org.archicontribs.modelrepository.preferences.IPreferenceConstants;
import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import com.archimatetool.editor.ui.UIUtils;
import com.archimatetool.editor.ui.components.ExtendedTitleAreaDialog;
import com.archimatetool.editor.utils.StringUtils;

/**
 * Commit (Save) Dialog
 * 
 * @author Phil Beauvoir
 */
public class CommitDialog extends ExtendedTitleAreaDialog {
    
    private static String DIALOG_ID = "CommitDialog"; //$NON-NLS-1$
    
    private Text fTextUserName, fTextUserEmail, fTextCommitMessage;
    private Button fAmendLastCommitCheckbox;
    
    private String fUserName, fUserEmail, fCommitMessage;
    private boolean fAmend;
    
    public CommitDialog(Shell parentShell) {
        super(parentShell, DIALOG_ID);
        setTitle(Messages.CommitDialog_0);
    }

    @Override
    protected void configureShell(Shell shell) {
        super.configureShell(shell);
        shell.setText(Messages.CommitDialog_0);
    }

    @Override
    protected Control createDialogArea(Composite parent) {
        setMessage(Messages.CommitDialog_1, IMessageProvider.INFORMATION);
        setTitleImage(IModelRepositoryImages.ImageFactory.getImage(IModelRepositoryImages.BANNER_COMMIT));

        Composite area = (Composite) super.createDialogArea(parent);
        Composite container = new Composite(area, SWT.NONE);
        container.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
        GridLayout layout = new GridLayout(2, false);
        container.setLayout(layout);
        
        // User name & email
        String userName = ModelRepositoryPlugin.INSTANCE.getPreferenceStore().getString(IPreferenceConstants.PREFS_COMMIT_USER_NAME);
        String userEmail = ModelRepositoryPlugin.INSTANCE.getPreferenceStore().getString(IPreferenceConstants.PREFS_COMMIT_USER_EMAIL);
        
        Label label = new Label(container, SWT.NONE);
        label.setText(Messages.CommitDialog_2);
        
        fTextUserName = new Text(container, SWT.BORDER);
        fTextUserName.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        fTextUserName.setText(userName);
        // Single text control so strip CRLFs
        UIUtils.conformSingleTextControl(fTextUserName);
        
        label = new Label(container, SWT.NONE);
        label.setText(Messages.CommitDialog_3);
        
        fTextUserEmail = new Text(container, SWT.BORDER);
        fTextUserEmail.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        fTextUserEmail.setText(userEmail);
        // Single text control so strip CRLFs
        UIUtils.conformSingleTextControl(fTextUserEmail);
        
        label = new Label(container, SWT.NONE);
        label.setText(Messages.CommitDialog_4);
        GridData gd = new GridData(GridData.FILL_HORIZONTAL);
        gd.horizontalSpan = 2;
        label.setLayoutData(gd);
        
        fTextCommitMessage = new Text(container, SWT.BORDER | SWT.V_SCROLL | SWT.WRAP | SWT.MULTI);
        gd = new GridData(GridData.FILL_BOTH);
        gd.horizontalSpan = 2;
        fTextCommitMessage.setLayoutData(gd);
        
        fAmendLastCommitCheckbox = new Button(container, SWT.CHECK);
        fAmendLastCommitCheckbox.setText(Messages.CommitDialog_5);
        gd = new GridData(GridData.FILL_HORIZONTAL);
        gd.horizontalSpan = 2;
        fAmendLastCommitCheckbox.setLayoutData(gd);
        
        if(!StringUtils.isSet(userName)) {
            fTextUserName.setFocus();
        }
        else if(!StringUtils.isSet(userEmail)) {
            fTextUserEmail.setFocus();
        }
        else {
            fTextCommitMessage.setFocus();
        }
        
        return area;
    }

    @Override
    protected boolean isResizable() {
        return true;
    }
    
    @Override
    protected Point getDefaultDialogSize() {
        return new Point(600, 450);
    }
    
    public String getUserName() {
        return fUserName;
    }

    public String getUserEmail() {
        return fUserEmail;
    }

    public String getCommitMessage() {
        return fCommitMessage;
    }
    
    public boolean getAmend() {
        return fAmend;
    }

    @Override
    protected void okPressed() {
        fUserEmail = fTextUserEmail.getText().trim();
        fUserName = fTextUserName.getText().trim();
        fCommitMessage = fTextCommitMessage.getText();
        fAmend =fAmendLastCommitCheckbox.getSelection();
        
        super.okPressed();
    }

}