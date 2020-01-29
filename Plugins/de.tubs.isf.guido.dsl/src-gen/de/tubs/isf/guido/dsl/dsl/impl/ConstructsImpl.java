/**
 * generated by Xtext 2.19.0
 */
package de.tubs.isf.guido.dsl.dsl.impl;

import de.tubs.isf.guido.dsl.dsl.Constructs;
import de.tubs.isf.guido.dsl.dsl.DslPackage;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Constructs</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link de.tubs.isf.guido.dsl.dsl.impl.ConstructsImpl#getElement <em>Element</em>}</li>
 *   <li>{@link de.tubs.isf.guido.dsl.dsl.impl.ConstructsImpl#getElements <em>Elements</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ConstructsImpl extends MinimalEObjectImpl.Container implements Constructs
{
  /**
   * The default value of the '{@link #getElement() <em>Element</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getElement()
   * @generated
   * @ordered
   */
  protected static final String ELEMENT_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getElement() <em>Element</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getElement()
   * @generated
   * @ordered
   */
  protected String element = ELEMENT_EDEFAULT;

  /**
   * The default value of the '{@link #getElements() <em>Elements</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getElements()
   * @generated
   * @ordered
   */
  protected static final String ELEMENTS_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getElements() <em>Elements</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getElements()
   * @generated
   * @ordered
   */
  protected String elements = ELEMENTS_EDEFAULT;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected ConstructsImpl()
  {
    super();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  protected EClass eStaticClass()
  {
    return DslPackage.Literals.CONSTRUCTS;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public String getElement()
  {
    return element;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setElement(String newElement)
  {
    String oldElement = element;
    element = newElement;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, DslPackage.CONSTRUCTS__ELEMENT, oldElement, element));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public String getElements()
  {
    return elements;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setElements(String newElements)
  {
    String oldElements = elements;
    elements = newElements;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, DslPackage.CONSTRUCTS__ELEMENTS, oldElements, elements));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Object eGet(int featureID, boolean resolve, boolean coreType)
  {
    switch (featureID)
    {
      case DslPackage.CONSTRUCTS__ELEMENT:
        return getElement();
      case DslPackage.CONSTRUCTS__ELEMENTS:
        return getElements();
    }
    return super.eGet(featureID, resolve, coreType);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void eSet(int featureID, Object newValue)
  {
    switch (featureID)
    {
      case DslPackage.CONSTRUCTS__ELEMENT:
        setElement((String)newValue);
        return;
      case DslPackage.CONSTRUCTS__ELEMENTS:
        setElements((String)newValue);
        return;
    }
    super.eSet(featureID, newValue);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void eUnset(int featureID)
  {
    switch (featureID)
    {
      case DslPackage.CONSTRUCTS__ELEMENT:
        setElement(ELEMENT_EDEFAULT);
        return;
      case DslPackage.CONSTRUCTS__ELEMENTS:
        setElements(ELEMENTS_EDEFAULT);
        return;
    }
    super.eUnset(featureID);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public boolean eIsSet(int featureID)
  {
    switch (featureID)
    {
      case DslPackage.CONSTRUCTS__ELEMENT:
        return ELEMENT_EDEFAULT == null ? element != null : !ELEMENT_EDEFAULT.equals(element);
      case DslPackage.CONSTRUCTS__ELEMENTS:
        return ELEMENTS_EDEFAULT == null ? elements != null : !ELEMENTS_EDEFAULT.equals(elements);
    }
    return super.eIsSet(featureID);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public String toString()
  {
    if (eIsProxy()) return super.toString();

    StringBuilder result = new StringBuilder(super.toString());
    result.append(" (element: ");
    result.append(element);
    result.append(", elements: ");
    result.append(elements);
    result.append(')');
    return result.toString();
  }

} //ConstructsImpl
