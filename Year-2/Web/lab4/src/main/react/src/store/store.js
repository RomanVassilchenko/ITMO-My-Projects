import {combineReducers, configureStore} from "@reduxjs/toolkit"
import resultTableSlice from "./resultTableSlice";
import xyrValuesSlice from "./xyrValuesSlice";
import tokenSlice from "./tokenSlice";

const rootReducer = combineReducers({
    resultTable: resultTableSlice,
    xyrValues: xyrValuesSlice,
    token: tokenSlice
})

export const tableStore = configureStore({
    reducer: rootReducer
})