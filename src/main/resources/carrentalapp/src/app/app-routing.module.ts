import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule, Routes } from '@angular/router';
import { CarComponent } from './cars/car.component';
import { ReservationsComponent } from './reservations/reservations.component';
import { LoginComponent } from './login/login.component';
import { UserComponent } from './user/user.component';
import { RegistrationComponent } from './registration/registration.component';
import { HomeComponent } from './home/home.component';
import { SelectedCarComponent } from './selected-car/selected-car.component';
import { ReservationSummaryComponent } from './reservation-summary/reservation-summary.component';
import { EditUserComponent } from './edit-user/edit-user.component';
import { AuthenticationGuard } from './authentication.guard';
import { FaqComponent } from './faq/faq.component';
import { SelectedReservationComponent } from './selected-reservation/selected-reservation.component';

const routes: Routes = [
    { path: '', canActivate:[AuthenticationGuard], children: [
    { path: '', component: HomeComponent},
    { path: 'login', component: LoginComponent },
    { path: 'cars', component: CarComponent },
    { path: 'reservations', component: ReservationsComponent },
    { path: 'users', component: UserComponent },
    { path: 'register', component: RegistrationComponent },
    { path: 'selected-car/:id', component: SelectedCarComponent},
    { path: 'selected-car/', component: SelectedCarComponent},
    { path: 'edit', component: EditUserComponent},
    { path: 'help', component: FaqComponent},
    { path: 'selected-reservation/:id', component: SelectedReservationComponent},
    { path: 'selected-reservation/', component: SelectedReservationComponent},
    { path: '**', redirectTo: '' }
  ]}
];


@NgModule({
  declarations: [],
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
