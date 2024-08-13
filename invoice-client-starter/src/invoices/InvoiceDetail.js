

import React, {useEffect, useState} from "react";
import {useParams} from "react-router-dom";
import { Link } from "react-router-dom";
import {apiGet} from "../utils/api";

const InvoiceDetail = () => {
    const {id} = useParams();
    const [invoice, setInvoice] = useState({});

    useEffect(() => {
        apiGet("/api/invoices/" + id).then((data) => setInvoice(data));
    }, [id]);

    if(invoice.buyer == null) {
        return (
            <div>
                <h2>Loading...</h2>
            </div>
    )
    }

    return (
        <>
            <div>
                <h1>Detail faktury</h1>
                <hr/>
                <h3></h3>
                <p>
                    <strong>Čislo faktury:</strong>
                    <br/>
                    {invoice.invoiceNumber}
                </p>
                <p>
                    <strong>Datum výdání:</strong>
                    <br/>
                    {invoice.issued}
                </p>
                <p>
                    <strong>Datum splatnosti:</strong>
                    <br/>
                    {invoice.dueDate}
                </p>
                <p>
                    <strong>Produkt:</strong>
                    <br/>
                    {invoice.product}
                </p>
                <p>
                    <strong>Cena:</strong>
                    <br/>
                    {invoice.price}
                </p>
                <p>
                    <strong>DPH:</strong>
                    <br/>
                    {invoice.vat}
                </p>
                <p>
                    <strong>Poznámka:</strong>
                    <br/>
                    {invoice.note}
                </p>
                <p>
                    <strong>Dodavatel:</strong>
                    <br/>
                    <Link to={`/persons/show/${invoice.seller._id}`} element={<InvoiceDetail />}>
                        {invoice.seller.name}
                    </Link>
                </p>
                <p>
                    <strong>Odběratel:</strong>
                    <br/>
                    <Link to={`/persons/show/${invoice.buyer._id}`} element={<InvoiceDetail />}>
                        {invoice.buyer.name}
                    </Link>
                </p>
            </div>
        </>
    );
};

export default InvoiceDetail;
