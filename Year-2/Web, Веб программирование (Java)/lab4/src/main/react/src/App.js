import React from 'react'

// pages
import Auth from "./components/auth/Auth";
import Main from "./components/main/Main";
import NotFound from "./components/notfound/NotFound";

// common elements for pages
import Header from './components/header/Header'
import Footer from "./components/footer/Footer";

// noinspection ES6CheckImport
import {Routes, Route} from 'react-router-dom'

// css styles
import './css/base.css'
import './css/general.css'

function App() {
    return (
        <div className="wrapper">
            <Header/>

            <Routes>
                <Route path="/" element={<Auth/>}/>
                <Route path="/main" element={<Main/>}/>
                <Route path="*" element={<NotFound/>}/>
            </Routes>

            <Footer/>
        </div>
    )
}

export default App;
