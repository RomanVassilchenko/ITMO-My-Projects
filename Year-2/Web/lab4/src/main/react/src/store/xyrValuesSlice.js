import {createSlice} from "@reduxjs/toolkit";

const xyrValuesSlice = createSlice({
    name: "xyrValues",
    initialState: {
        x: {value: 0, validity: true},
        y: {value: 0, validity: false},
        r: {value: 1, validity: true}
    },
    reducers: {
        updateValues(state, action) {
            return {...state, ...action.payload};
        }
    }
})

export default xyrValuesSlice.reducer
export const {updateValues} = xyrValuesSlice.actions