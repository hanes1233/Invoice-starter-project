/*  _____ _______         _                      _
 * |_   _|__   __|       | |                    | |
 *   | |    | |_ __   ___| |___      _____  _ __| | __  ___ ____
 *   | |    | | '_ \ / _ \ __\ \ /\ / / _ \| '__| |/ / / __|_  /
 *  _| |_   | | | | |  __/ |_ \ V  V / (_) | |  |   < | (__ / /
 * |_____|  |_|_| |_|\___|\__| \_/\_/ \___/|_|  |_|\_(_)___/___|
 *                                _
 *              ___ ___ ___ _____|_|_ _ _____
 *             | . |  _| -_|     | | | |     |  LICENCE
 *             |  _|_| |___|_|_|_|_|___|_|_|_|
 *             |_|
 *
 *   PROGRAMOVÁNÍ  <>  DESIGN  <>  PRÁCE/PODNIKÁNÍ  <>  HW A SW
 *
 * Tento zdrojový kód je součástí výukových seriálů na
 * IT sociální síti WWW.ITNETWORK.CZ
 *
 * Kód spadá pod licenci prémiového obsahu a vznikl díky podpoře
 * našich členů. Je určen pouze pro osobní užití a nesmí být šířen.
 * Více informací na http://www.itnetwork.cz/licence
 */

import React from "react";
import "bootstrap/dist/css/bootstrap.min.css";
import './components/css/style.css';
import {
  BrowserRouter as Router,
  Link,
  Route,
  Routes,
  Navigate,
} from "react-router-dom";

import PersonIndex from "./persons/PersonIndex";
import PersonDetail from "./persons/PersonDetail";
import PersonForm from "./persons/PersonForm";
import InvoiceDetail from "./invoices/InvoiceDetail";
import InvoiceIndex from "./invoices/InvoiceIndex";
import InvoiceForm from "./invoices/InvoiceForm";
import { Statistics } from "./statistics/Statistics";


export function App() {
  return (
    <Router>
      <div className="container">
        <nav className="navbar navbar-expand-lg ">
          <ul className="navbar-nav mr-auto">
            <li className="nav-item border bg-secondary zoom">
              <Link to={"/persons"} className="nav-link text-light clr">
                Osoby
              </Link>
            </li>
            <li className="nav-item border bg-secondary zoom">
              <Link to={"/invoices"} className="nav-link text-light clr">
                Faktury
              </Link>
            </li>
            <li className="nav-item border bg-secondary zoom">
              <Link to={"/statistics"} className="nav-link text-light clr">
                Statistiky
              </Link>
            </li>
          </ul>
        </nav>


        <Routes>
          <Route index element={<Navigate to={"/persons"} />} />
          <Route path="/persons">
            <Route index element={<PersonIndex />} />
            <Route path="show/:id" element={<PersonDetail />} />
            <Route path="create" element={<PersonForm />} />
            <Route path="edit/:id" element={<PersonForm />} />
          </Route>
        </Routes>

        <Routes>
          <Route index element={<Navigate to={"/invoices"} />} />
          <Route path="/invoices">
            <Route index element={<InvoiceIndex />} />
            <Route path="show/:id" element={<InvoiceDetail />} />
            <Route path="create" element={<InvoiceForm />} />
            <Route path="edit/:id" element={<InvoiceForm />} />
          </Route>
        </Routes>

        <Routes>
          <Route path="/statistics">
            <Route index element={<Statistics />} />
          </Route>
        </Routes>
      </div>
    </Router>
  );
}

export default App;
