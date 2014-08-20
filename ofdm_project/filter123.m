function b = filter
%FILTER Returns a discrete-time filter object.

% MATLAB Code
% Generated by MATLAB(R) 8.3 and the Signal Processing Toolbox 6.21.
% Generated on: 28-Apr-2014 01:14:23

% FIR Window Lowpass filter designed using the FIR1 function.

% All frequency values are in MHz.
Fs = 30.72;  % Sampling Frequency

Fpass = 4.3;              % Passband Frequency
Fstop = 5;                % Stopband Frequency
Dpass = 0.28012999961;    % Passband Ripple
Dstop = 0.0056234132519;  % Stopband Attenuation
flag  = 'scale';          % Sampling Flag

% Calculate the order from the parameters using KAISERORD.
[N,Wn,BETA,TYPE] = kaiserord([Fpass Fstop]/(Fs/2), [1 0], [Dstop Dpass]);

% Calculate the coefficients using the FIR1 function.
b  = fir1(N, Wn, TYPE, kaiser(N+1, BETA), flag);
%Hd = dfilt.dffir(b);

% [EOF]
