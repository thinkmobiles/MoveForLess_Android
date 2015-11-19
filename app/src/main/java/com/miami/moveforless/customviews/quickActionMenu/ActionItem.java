package com.miami.moveforless.customviews.quickActionMenu;

/**
 * Action item, displayed as menu with icon and text.
 *
 * @author Lorensius. W. L. T <lorenz@londatiga.net>
 *         <p>
 *         Contributors:
 *         - Kevin Peck <kevinwpeck@gmail.com>
 */
public class ActionItem {

    private int icon;
    private String title;
    private int actionId = -1;

    /**
     * Constructor
     *
     * @param actionId Action id for case statements
     * @param title    Title
     * @param icon     Icon to use
     */
    public ActionItem(int actionId, String title, int icon) {
        this.title = title;
        this.icon = icon;
        this.actionId = actionId;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }


    /**
     * Set action title
     *
     * @param title action title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Get action title
     *
     * @return action title
     */
    public String getTitle() {
        return this.title;
    }

    /**
     * @return Our action id
     */
    public int getActionId() {
        return actionId;
    }

}