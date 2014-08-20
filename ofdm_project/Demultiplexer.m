function [ serial_output ] = Demultiplexer( Demapped_Data )
symbolcounter=1;
Demultiplexed_Data = zeros(300,140);
serial_output = zeros(1,40000);
serial_counter=1;

for m = 1:1:140
    counter =1;
    symbolcounter= mod(m,7);
   if(symbolcounter == 1)
       for a=1:1:300
           if(mod(a,6) ~= 1)
               Demultiplexed_Data(counter,m)=Demapped_Data(a,m);
               serial_output(1,serial_counter) = Demapped_Data(a,m);
               serial_counter = serial_counter+1;
               
               counter=counter+1;
           end
       end
   end
   
   
    if(symbolcounter == 5)
       for a=1:1:300
           if(mod(a,6) ~= 4)
               Demultiplexed_Data(counter,m)=Demapped_Data(a,m);
               counter=counter+1;
               serial_output(1,serial_counter) = Demapped_Data(a,m);
               serial_counter = serial_counter+1;
           end
       end
   end
   if (symbolcounter ~= 1 && symbolcounter ~= 5)
       for a=1:1:300           
           Demultiplexed_Data(counter,m)=Demapped_Data(a,m);
           counter=counter+1;
           serial_output(1,serial_counter) = Demapped_Data(a,m);
               serial_counter = serial_counter+1;
       end
   end
           
   
   
   
   
    
%UNTITLED6 Summary of this function goes here
%   Detailed explanation goes here


end

