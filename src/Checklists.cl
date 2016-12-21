
[Mappings]
toast : android.widget.Toast.show()V, main;


[Checklists]

buttonEqualsClick: buttonclick, calculate , !showError =>  updateDisplay;
buttonEqualsClickError: buttonclick, calculate, showError => toast, clearDisplay;

buttonNextClick: buttonclick, startScientificActivity => saveState, createScientificActivity, setupGUI, restoreState;

