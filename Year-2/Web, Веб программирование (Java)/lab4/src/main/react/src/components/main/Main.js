import React from 'react';
import {useDispatch, useSelector} from "react-redux";

import Canvas from "./canvas/Canvas";
import Form from "./form/Form";
import Table from "./table/Table";

// css styles
import '../../css/main.css'
import {updateTable} from "../../store/resultTableSlice";

let isInitialize = false

function Main() {
    const dispatch = useDispatch();
    const TOKEN = useSelector(state => state.token);

    const getResultData = () => {
        return fetch('http://localhost:8080/lab4/api/results', {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',
                'AUTHORIZATION': TOKEN
            }}).then((res) => {
            if (res.ok) {
                return res.json()
            }
        })
    }

    if (!isInitialize) {
        getResultData().then(tableData => dispatch(updateTable(tableData)));
        isInitialize = true;
    }

    const resultList = useSelector(state => state.resultTable);

    return (
        <main className="main">
            <div className="container">
                <h1 className="main__title">Лабораторная работа #4, вариант 8432</h1>

                <div className="main__row">
                    <div className="main__left-block">
                        <Canvas/>
                        <Form/>
                    </div>

                    <div className="main__table-block">
                        <Table resultList={resultList}/>
                    </div>
                </div>
            </div>
        </main>
    )
}

export default Main
