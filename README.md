# PhoneBook

The window is divided into two sections:  
- On the **left**, there is a list of contacts.  
- On the **right**, the details of the selected contact are displayed (personal information and addresses).

---

## Features

- If the user changes the Name or Surname of a contact and then selects another contact, a message will appear asking whether they want to save the changes or not, with options **Yes** or **No**.

- The window includes a **File** menu with three options:  
  - **Open** (disabled)  
  - **Close**  
  - **Exit**

- When the user clicks **Close**, a dialog appears checking if there are unsaved changes in the selected contact. The dialog offers three options:  
  - **Yes**: saves the changes before closing the phonebook.  
  - **No**: closes the phonebook without saving the changes.  
  - **Cancel**: closes the dialog and keeps the phonebook open.

- The **Exit** option follows the same logic as **Close**.

- When the user closes the main window, the same logic as **Close** is applied.

- If the user clicks the **Edit** button, a new window opens showing the address details. This window has two options:  
  - **OK**: updates the contact with the new address details.  
  - **Cancel**: closes the window without saving changes.
