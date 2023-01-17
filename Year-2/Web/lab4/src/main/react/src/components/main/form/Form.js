import React from 'react';

import {useDispatch, useSelector} from "react-redux";
import {addResult, clearTable} from "../../../store/resultTableSlice";
import {validateTextInput} from "../../../utils/validation";

import ListBox from "./list_box/ListBox";
import {updateValues} from "../../../store/xyrValuesSlice";
import { useState } from "react";

function Form() {
    const dispatch = useDispatch();
    const {x, y, r} = useSelector(state => state.xyrValues);
    const TOKEN = useSelector(state => state.token);

    const isSubmitAvailable = () => (x.validity && y.validity && r.validity);

    function validateInput(inputField) {
        const validityResult = validateTextInput(inputField);

        dispatch(updateValues({
            [inputField.name]: {value: parseFloat(inputField.value), validity: validityResult}
        }));
    }

    function validateListBoxItem(listBox) {
        dispatch(updateValues({
            [listBox.name]: {value: parseFloat(listBox.value), validity: true}
        }))
    }

    function validateSliderItem(slider) {
        setValue(slider.valueAsNumber);
        dispatch(updateValues({
            [slider.name]: {value: parseFloat(slider.value), validity: true}
        }))
    }

    const submitHandler = (event) => {
        event.preventDefault();

        fetch('http://localhost:8080/lab4/api/results', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'AUTHORIZATION': TOKEN
            },
            body: JSON.stringify({
                x: x.value,
                y: y.value,
                r: r.value
            })
        }).then((res) => {
            if (res.ok) {
                return res
                    .json()
                    .then(newResult => dispatch(addResult(newResult)))
            }
        })
    }

    const clearFields = () => {

    }

    const clearHandler = (event) => {
        event.preventDefault();
        clearFields();
        fetch('http://localhost:8080/lab4/api/results', {
            method: 'DELETE',
            headers: {
                'Content-Type': 'application/json',
                'AUTHORIZATION': TOKEN
            }
        }).then((res) => {
            if (res.ok) {
                dispatch(clearTable());
            }
        })
    }

    const [value, setValue] = useState(0);

    return (
        <form className="form" method="get">
            <label className="form__label">X</label>
            <div className="form__row">
                <ListBox name="x"
                         items={generateItems(-2, 2, 0.5)}
                         selected={0}
                         onInput={event => validateListBoxItem(event.target)}/>
            </div>

            <label className="form__label">Y</label>
            <div className="form__row">
                <input name="y"
                       required className="form__number-input"
                       type="range" step="any" min="-5" max="5"
                       placeholder="Введите значение [-5 ... 5]"
                       value={value}
                       onInput={event => validateSliderItem(event.target)}/>
            </div>

            <label className="form__label">R</label>
            <div className="form__row">
                <ListBox name="r" items={generateItems(0.5, 2, 0.5)}
                         selected={r.value}
                         onInput={event => validateListBoxItem(event.target)}/>
            </div>

            <div className="form__row">
                <button className="form__big-btn" type="submit"
                        disabled={!isSubmitAvailable()}
                        onClick={event => submitHandler(event)}>Отправить</button>
                <button className="form__big-btn" type="reset"
                        onClick={event => clearHandler(event)}>Очистить</button>
            </div>
        </form>
    )
}

export default Form

function generateItems(min, max, step) {
    const xItems = [];
    let id = 0;
    for (let x = min; x <= max; x+=step) {
        xItems.push({
            id: id,
            value: x
        });
        id++;
    }
    return xItems;
}
