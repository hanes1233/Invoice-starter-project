
import React, { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import { Link } from "react-router-dom";

import { apiGet } from "../utils/api";
import Country from "./Country";
import InvoiceDetail from "../invoices/InvoiceDetail";

const PersonDetail = () => {
    const { id } = useParams();
    const [person, setPerson] = useState({});
    const [invoices, setInvoices] = useState([]);

    useEffect(() => {
        apiGet("/api/persons/" + id).then((data) => setPerson(data));
        apiGet("/api/invoices/person/" + id).then((data) => setInvoices(data));
    }, [id]);

    while (person.identificationNumber == null) {
        return (
            <h1>Načítání...</h1>
        )
    }

    const country = Country.CZECHIA === person.country ? "Česká republika" : "Slovensko";

    return (
        <>
            <div>
                <div className="row">
                    <div className="col-6">
                        <h1>Detail osoby</h1>
                        <hr />
                        <h3>{person.name} ({person.identificationNumber})</h3>
                        <p>
                            <strong>DIČ:</strong>
                            <br />
                            {person.taxNumber}
                        </p>
                        <p>
                            <strong>Bankovní účet:</strong>
                            <br />
                            {person.accountNumber}/{person.bankCode} ({person.iban})
                        </p>
                        <p>
                            <strong>Tel.:</strong>
                            <br />
                            {person.telephone}
                        </p>
                        <p>
                            <strong>Mail:</strong>
                            <br />
                            {person.mail}
                        </p>
                        <p>
                            <strong>Sídlo:</strong>
                            <br />
                            {person.street}, {person.city},
                            {person.zip}, {country}
                        </p>
                        <p>
                            <strong>Poznámka:</strong>
                            <br />
                            {person.note}
                        </p>
                    </div>
                    <div className="col-6">
                        <div className="col-6">
                            <table className="table table-bordered table-striped border table-color">
                                <thead>
                                    <tr>
                                        <th>Čislo faktury:</th>
                                        <th>Produkt:</th>
                                        <th>Cena:</th>
                                        <th>DPH:</th>
                                        <th>Dodavatel:</th>
                                        <th>Odběratel:</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    {invoices.map((invoice, index) => (
                                        <tr key={index + 1}>
                                            <td>
                                                <Link to={`/invoices/show/${invoice._id}`} element={<InvoiceDetail />}>
                                                    {invoice.invoiceNumber}
                                                </Link>
                                            </td>
                                            <td>{invoice.product}</td>
                                            <td>{invoice.price}</td>
                                            <td>{invoice.vat}</td>
                                            <td>{invoice.seller.name}</td>
                                            <td>{invoice.buyer.name}</td>
                                        </tr>
                                    ))}
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
        </>
    );
};

export default PersonDetail;
