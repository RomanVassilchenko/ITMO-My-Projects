import React from 'react'
import AuthForm from "./form/AuthForm";

// css styles
import '../../css/general.css'
import '../../css/auth.css'

function Auth() {
    return (
        <main className="main">
            <div className="container">
                <h1 className="main__title">Лабораторная работа #4, вариант 8432</h1>

                <div className="main__row">
                    <div className="main__left-block">
                        <AuthForm/>
                    </div>
                </div>
            </div>
        </main>
    )
}

export default Auth;
