import React from 'react';

// css styles
import '../../css/header.css'
import {useNavigate} from 'react-router-dom';

function Header() {
    const navigate = useNavigate();

    const logoutHandler = (event) => {
        event.preventDefault();
        localStorage.removeItem('token');
        window.location.href = '/';
    }

    return (
        <header className="header">
            <div className="container">
                <div className="header__row">
                    <div className="header__title">
                        <h1>Васильченко Роман, P32081</h1>
                    </div>
                    <div className="header__menu menu">
                        <div className="menu__icon" onClick={event => menuIconClickCallback(event.target)}>
                            <span/>
                        </div>
                        <div className="menu__body">
                            <ul className="menu__list">
                                <li className="menu__item">
                                    <a href="#" onClick={event => logoutHandler(event)}>Выйти из аккаунта</a>
                                </li>
                            </ul>
                        </div>
                    </div>
                </div>
            </div>
        </header>
    )
}

Header.propTypes = {}

export default Header

function menuIconClickCallback(iconMenu) {
    iconMenu.classList.toggle('_active');
    document.querySelector('.header__title').classList.toggle('_active');
    document.querySelector('.menu__body').classList.toggle('_active');
    document.body.classList.toggle('_lock');
}