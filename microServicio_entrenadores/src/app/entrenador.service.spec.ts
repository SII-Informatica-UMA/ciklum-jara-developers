import { TestBed } from '@angular/core/testing';

import { ContactosService } from './entrenador.service';

describe('ContactosService', () => {
  let service: ContactosService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ContactosService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
