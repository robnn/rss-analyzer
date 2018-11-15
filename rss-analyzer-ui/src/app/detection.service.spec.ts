import { TestBed, inject } from '@angular/core/testing';

import { DetectionService } from './detection.service';

describe('DetectionService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [DetectionService]
    });
  });

  it('should be created', inject([DetectionService], (service: DetectionService) => {
    expect(service).toBeTruthy();
  }));
});
