/**
 * This is an Observable model to view in Model-View-Controller Architecture.
 */
package ca.soen6441.tf.critterstd.model;

import ca.soen6441.tf.critterstd.view.ViewObserver;

/**
 * This interface will give the methods of an observable model to views in MVC
 * architecture.
 * 
 * @author Farzana Alam
 *
 */
public interface ModelObservable {

	/**
	 * @return the respective model's updated state.
	 */
	public ModelObservable getState();

	/**
	 * Registers the view observer to this model.
	 * 
	 * @param observer
	 *            the view that will observe this model.
	 */
	public void attach(ViewObserver observer);

	/**
	 * Notifies all the observers bound to this model.
	 */
	void notifyAllObservers();

}
