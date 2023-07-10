import React from 'react'
import {useStateWithCallbackLazy} from 'use-state-with-callback';

import {validateTextInput} from "../../../utils/validation";
import {useDispatch} from "react-redux";
import {updateToken} from "../../../store/tokenSlice";
import {useNavigate} from 'react-router-dom';

function AuthForm() {
    const dispatch = useDispatch();
    const navigate = useNavigate();

    const [inputValues, setInputValues] = useStateWithCallbackLazy({
        username: {value: '', validity: false},
        password: {value: '', validity: false}
    });

    const [authMsg, setAuthMsg] = React.useState({
        success: true,
        message: ''
    })

    const [isButtonsAvailable, setButtonsAvailable] = React.useState(false);

    const toggleButtons = (inputValues) => {
        if (inputValues.username.validity && inputValues.password.validity) {
            setButtonsAvailable(true);
        } else {
            setButtonsAvailable(false);
        }
    }

    const validateInput = (inputField) => {
        const validityResult = validateTextInput(inputField)

        setInputValues({
            ...inputValues,
            [inputField.name]: {value: inputField.value, validity: validityResult}
        }, toggleButtons);
    }

    const authHandler = (event, authType) => {
        event.preventDefault();
        fetch(`http://localhost:8080/lab4/api/auth/${authType}`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({
                username: inputValues.username.value,
                password: inputValues.password.value
            })
        }).then((res) => {
            if (res.ok) {
                res.json().then(tokenWrapper => {
                        dispatch(updateToken(tokenWrapper.token));
                        localStorage.setItem('token', tokenWrapper.token);
                        navigate('/main', {replace: true});
                    }
                )
            } else {
                res.text().then((message) =>
                    setAuthMsg({
                        success: false,
                        message: message
                    })
                )
            }
        })
    }

    return (
        <>
            <p className={`auth-message ${authMsg.success ? 'success' : 'failed'}`}>{authMsg.message}</p>

            <form className="form form-auth">
                <label className="form__label">Логин</label>
                <div className="form__row">
                    <input name="username" onInput={event => validateInput(event.target)}
                           required className="form__number-input"
                           type="text" defaultValue=""/>
                </div>

                <label className="form__label">Пароль</label>
                <div className="form__row">
                    <input name="password" onInput={event => validateInput(event.target)}
                           required className="form__number-input"
                           type="password" defaultValue=""/>
                </div>

                <div className="form__row">
                    <button className="form__big-btn" disabled={!isButtonsAvailable} type="sign_in"
                            onClick={event => authHandler(event, 'login')}>Войти
                    </button>
                    <button className="form__big-btn" disabled={!isButtonsAvailable} type="sign_up"
                            onClick={event => authHandler(event, 'register')}>Регистрация
                    </button>
                </div>
            </form>
        </>
    )
}

export default AuthForm;
