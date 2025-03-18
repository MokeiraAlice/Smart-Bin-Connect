import { Routes } from '@angular/router';
import { DashboardComponent } from './components/dashboard/dashboard.component';
import { RequestFormComponent } from './components/request-form/request-form.component';

// import { MapViewComponent } from './map-view/map-view.component';
// import { CollectionRequestsComponent } from './collection-requests/collection-requests.component';




export const routes: Routes = [
    { path: 'dashboard', component: DashboardComponent },
    { path: 'request-form', component: RequestFormComponent },
    { path: '', redirectTo: '/dashboard', pathMatch: 'full' },
    // { path: 'map', component: MapViewComponent },
    // { path: 'requests', component: CollectionRequestsComponent },
];
