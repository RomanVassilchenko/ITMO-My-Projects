import {createSlice} from "@reduxjs/toolkit";

const resultTableSlice = createSlice({
    name: "resultTable",
    initialState: [],
    reducers: {
        updateTable(state, action) {
            return action.payload;
        },

        addResult(state, action) {
            return state.concat(action.payload);
        },

        clearTable() {
            return [];
        }
    }
})

export default resultTableSlice.reducer
export const {updateTable, addResult, clearTable} = resultTableSlice.actions