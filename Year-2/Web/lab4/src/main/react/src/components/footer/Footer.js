import React from 'react';

// css styles
import '../../css/footer.css'

function Footer() {
    return (
        <footer className="footer">
            <div className="container">
                <div className="footer__text">
                    <a className="footer__title" href="#">© 2023 / ИТМО ВТ</a>
                </div>
            </div>
        </footer>
    )
}

Footer.propTypes = {}

export default Footer