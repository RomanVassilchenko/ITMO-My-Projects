import React from 'react';

function TableRow({ result }) {
    return (
        <tr>
            <td>{result.x}</td>
            <td>{result.y}</td>
            <td>{result.r}</td>
            <td>{getHitSpan(result.hit)}</td>
        </tr>
    )
}

export default TableRow

function getHitSpan(isHit) {
    const className = isHit ? 'hit' : 'miss';
    const textContent = isHit ? 'Попадание' : 'Промах';
    return (
        <span className={className}>{textContent}</span>
    )
}