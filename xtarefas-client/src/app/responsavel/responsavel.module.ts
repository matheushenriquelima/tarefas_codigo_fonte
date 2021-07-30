import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { ResponsavelRoutingModule } from './responsavel-routing.module';
import { ResponsavelComponent } from './responsavel/responsavel.component';
import { SharedModule } from '../shared/shared.module';


@NgModule({
  declarations: [ResponsavelComponent],
  imports: [
    CommonModule,
    ResponsavelRoutingModule,
    SharedModule
  ]
})
export class ResponsavelModule { }
