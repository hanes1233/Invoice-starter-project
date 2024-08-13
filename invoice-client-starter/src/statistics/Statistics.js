import { useState, useEffect } from "react";
import { apiGet } from "../utils/api";

export function Statistics() {
    const [statistics, setStatistics] = useState([]);
    const [persons, setPersons] = useState([]);

    useEffect(() => {
        apiGet("/api/invoices/statistics").then((data) => setStatistics(data));
    }, []);

    useEffect(() => {
        apiGet("/api/persons/statistics").then((data) => setPersons(data));
    }, []);

    if ((statistics == null) || (persons == null)) {
        return (
            <div>
                <h3>Načítání...</h3>
            </div>
        )
    }


    return (
        <div className="container">
            <div className="col">
                <div className="row mt-5">
                    <div className="col-6">
                        <div className="card">
                            <div className="card-body">
                                <h3>Vypis statistik</h3>
                                <br />
                                <p>Součet cen za rok: <strong>{statistics.currentYearSum} Kč</strong></p>
                                <p>Součet cen za všechny roky: <strong>{statistics.allTimeSum} Kč</strong></p>
                                <p>Počet faktur: <strong>{statistics.invoiceCount}</strong></p>
                            </div>
                        </div>
                    </div>
                    <div className="col-6">
                        <table className="table table-bordered table-striped border table-color">
                            <thead>
                                <tr>
                                    <th>Id</th>
                                    <th>Jméno</th>
                                    <th>Přijmy</th>
                                </tr>
                            </thead>
                            <tbody>
                                    {persons.map((item) => (
                                        <tr>
                                            <td>{item.personId}</td>
                                            <td>{item.personName}</td>
                                            <td>{item.revenue}</td>
                                        </tr>
                                    ))}
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    )
}