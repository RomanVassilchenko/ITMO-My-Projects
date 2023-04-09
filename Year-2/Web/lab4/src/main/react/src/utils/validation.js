export function validateTextInput(inputField) {
    const validityState = inputField.validity;

    let validityResult = false;

    if (validityState.valueMissing) {
        inputField.setCustomValidity('Поле не может быть пустым.');

    } else if (validityState.rangeUnderflow || validityState.rangeOverflow) {
        inputField.setCustomValidity('Значение должно находиться в отрезке [-5 ... 3].');
    } else {
        validityResult = true;
        inputField.setCustomValidity('');
    }
    return validityResult;
}