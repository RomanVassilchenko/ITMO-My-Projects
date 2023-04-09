import React from 'react';
import ReactDOM from 'react-dom/client';
import {Provider} from "react-redux";
import  {BrowserRouter} from 'react-router-dom'

import {tableStore} from "./store/store";
import App from './App';
import reportWebVitals from './reportWebVitals';

const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(
    <React.StrictMode>
        <BrowserRouter>
            <Provider store={tableStore}>
                <App/>
            </Provider>
        </BrowserRouter>
    </React.StrictMode>
);

reportWebVitals();